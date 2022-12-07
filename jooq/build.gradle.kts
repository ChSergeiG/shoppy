import com.bmuschko.gradle.docker.tasks.container.DockerStartContainer
import com.bmuschko.gradle.docker.tasks.container.DockerStopContainer
import com.bmuschko.gradle.docker.tasks.image.DockerPullImage
import ru.chsergeig.shoppy.gradle.JAVA_COMPATIBILITY
import ru.chsergeig.shoppy.gradle.LIQUIBASE_VERSION
import ru.chsergeig.shoppy.gradle.POSTGRES_VERSION
import ru.chsergeig.shoppy.gradle.task.CheckDockerContainerHealthy
import ru.chsergeig.shoppy.gradle.task.GetDockerImageIdTask
import ru.chsergeig.shoppy.gradle.task.createPostgresContainerConfig
import ru.chsergeig.shoppy.gradle.task.jooqCodeGenConfig

plugins {
    `java-library`
    id("org.liquibase.gradle") version "2.1.0"
    id("com.bmuschko.docker-remote-api")
}

spotless {
    java {
        targetExclude("src/main/java//**/*.*")
    }
}

dependencies {
    api(group = "org.jooq", name = "jooq", version = "3.16.5")

    liquibaseRuntime(group = "com.h2database", name = "h2", version = "2.1.212")
    liquibaseRuntime(group = "jakarta.xml.bind", name = "jakarta.xml.bind-api", version = "3.0.1")
    liquibaseRuntime(group = "org.postgresql", name = "postgresql", version = POSTGRES_VERSION)
    liquibaseRuntime(group = "org.liquibase", name = "liquibase-core", version = LIQUIBASE_VERSION)
}

java {
    sourceCompatibility = JAVA_COMPATIBILITY
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
    val postgresImage = "postgres"

    val pullPostgresImage = register<DockerPullImage>("pullPostgresImage") {
        image.set(postgresImage)
    }

    val inspectPostgresImage = register<GetDockerImageIdTask>("inspectPostgresImage") {
        dependsOn(pullPostgresImage)
        filterForImageName.set(postgresImage)
    }

    val createPostgresContainer = register(
        "createPostgresContainer",
        createPostgresContainerConfig(
            inspectPostgresImage.get().imageId,
            inspectPostgresImage
        )
    )

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

    val jooqCodeGen = register(
        "jooqCodeGen",
        jooqCodeGenConfig(
            stopPostgresContainer,
            clean, update, waitPostgresContainer
        )
    )

    update {
        dependsOn(waitPostgresContainer)
    }

    jar {
        enabled = true
    }
}
