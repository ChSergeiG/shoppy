import com.bmuschko.gradle.docker.domain.LivenessProbe
import com.bmuschko.gradle.docker.tasks.container.DockerCreateContainer
import com.bmuschko.gradle.docker.tasks.container.DockerStartContainer
import com.bmuschko.gradle.docker.tasks.container.DockerStopContainer
import com.bmuschko.gradle.docker.tasks.image.DockerPullImage
import ru.chsergeig.shoppy.gradle.LIQUIBASE_VERSION
import ru.chsergeig.shoppy.gradle.SOURCE_COMPATIBILITY
import ru.chsergeig.shoppy.gradle.task.CheckDockerContainerHealthy
import ru.chsergeig.shoppy.gradle.task.GetDockerImageIdTask
import ru.chsergeig.shoppy.gradle.task.JooqGenerateClasses
import java.util.concurrent.atomic.AtomicReference

plugins {
    `java-library`
    id("org.liquibase.gradle") version "2.1.0"
}

apply(plugin = "com.bmuschko.docker-remote-api")

dependencies {
    api(group = "org.jooq", name = "jooq", version = "3.16.5")

    liquibaseRuntime(group = "org.postgresql", name = "postgresql", version = "42.2.18")
    liquibaseRuntime(group = "org.liquibase", name = "liquibase-core", version = LIQUIBASE_VERSION)
    liquibaseRuntime(group = "jakarta.xml.bind", name = "jakarta.xml.bind-api", version = "3.0.1")
}

java {
    sourceCompatibility = SOURCE_COMPATIBILITY
}

liquibase {
    activities {
        register("main") {
            arguments = mapOf(
                "logLevel" to "info",
                "driver" to "org.postgresql.Driver",
                "changeLogFile" to "src/main/resources/liquibase/main.xml",
                "url" to "jdbc:postgresql://localhost:5433/shoppy",
                "username" to "shoppy",
                "password" to "shoppy",
            )
        }
    }
    runList = "main"
}

tasks {
    val postgresImageId: AtomicReference<String> = AtomicReference("")

    val pullPostgresImage = register<DockerPullImage>("pullPostgresImage") {
        image.set("postgres:13.2")
    }

    val inspectPostgresImage = register<GetDockerImageIdTask>("inspectPostgresImage") {
        dependsOn(pullPostgresImage)
        filterForImageName.set("postgres:13.2")
        doLast {
            postgresImageId.set(imageId.get())
        }
    }

    val createPostgresContainer = register<DockerCreateContainer>("createPostgresContainer") {
        dependsOn(inspectPostgresImage)
        imageId.set(postgresImageId.get())
        image.set("postgres:13.2")
        hostConfig.portBindings.add("5433:5432")
        withEnvVar("POSTGRES_DB", "shoppy")
        withEnvVar("POSTGRES_USER", "shoppy")
        withEnvVar("POSTGRES_PASSWORD", "shoppy")
        healthCheck.cmd("pg_isready -U shoppy")
        hostConfig.autoRemove.set(true)
    }

    val startPostgresContainer = register<DockerStartContainer>("startPostgresContainer") {
        dependsOn(createPostgresContainer)
        targetContainerId(createPostgresContainer.get().containerId)
    }

    val waitPostgresContainer = register<CheckDockerContainerHealthy>("waitPostgresContainer") {
        dependsOn(startPostgresContainer)
        containerId.set(createPostgresContainer.get().containerId)
        livenessProbe(logContains = "database system is ready to accept connections")
    }

    val stopPostgresContainer = register<DockerStopContainer>("stopPostgresContainer") {
        containerId.set(createPostgresContainer.get().containerId)
    }

    update {
        dependsOn(waitPostgresContainer)
    }

    val jooqCodeGen = register<JooqGenerateClasses>("jooqCodeGen") {
        dependsOn(clean, update, waitPostgresContainer)
        finalizedBy(stopPostgresContainer)
        jdbcUrl.set("jdbc:postgresql://localhost:5433/shoppy")
        jdbcUsername.set("shoppy")
        jdbcPassword.set("shoppy")
        targetDirectory.set("src/main/java")
        targetPackage.set("ru.chsergeig.shoppy.jooq")
    }

    compileJava {
        dependsOn(jooqCodeGen)
    }

    jar {
        dependsOn(compileJava)
        enabled = true
    }
}
