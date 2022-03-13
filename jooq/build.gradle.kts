import com.bmuschko.gradle.docker.tasks.container.DockerCreateContainer
import com.bmuschko.gradle.docker.tasks.container.DockerStartContainer
import com.bmuschko.gradle.docker.tasks.container.DockerStopContainer
import com.bmuschko.gradle.docker.tasks.image.DockerPullImage
import org.jooq.codegen.GenerationTool
import org.jooq.meta.jaxb.Configuration
import org.jooq.meta.jaxb.Database
import org.jooq.meta.jaxb.Generate
import org.jooq.meta.jaxb.Generator
import org.jooq.meta.jaxb.Jdbc
import org.jooq.meta.jaxb.Logging
import org.jooq.meta.jaxb.Strategy
import org.jooq.meta.jaxb.Target
import ru.chsergeig.shoppy.gradle.LIQUIBASE_VERSION
import ru.chsergeig.shoppy.gradle.SOURCE_COMPATIBILITY
import ru.chsergeig.shoppy.gradle.task.CheckDockerContainerHealthy
import ru.chsergeig.shoppy.gradle.task.CheckDockerContainerHealthy.CheckLevel
import ru.chsergeig.shoppy.gradle.task.GetDockerImageIdTask
import java.util.concurrent.atomic.AtomicReference

buildscript {
    configurations.all {
        resolutionStrategy.eachDependency {
            when (requested.group) {
                "org.jooq" -> useVersion("3.16.5")
            }
        }
    }
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(group = "jakarta.xml.bind", name = "jakarta.xml.bind-api", version = "3.0.1")
        classpath(group = "org.jooq", name = "jooq-codegen", version = "3.16.5")
        classpath(group = "org.postgresql", name = "postgresql", version = "42.2.18")
    }
}

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
                "url" to "jdbc:postgresql://localhost:5432/shoppy",
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
        hostConfig.portBindings.add("5432:5432")
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
        taskTimeout.set(30)
        checkInterval.set(1)
        checkLevel.set(CheckLevel.LOW)
    }

    val stopPostgresContainer = register<DockerStopContainer>("stopPostgresContainer") {
        containerId.set(createPostgresContainer.get().containerId)
    }

    update {
        dependsOn(waitPostgresContainer)
    }

    val jooqCodeGen = register<DefaultTask>("jooqCodeGen") {
        dependsOn(clean, update, waitPostgresContainer)
        finalizedBy(stopPostgresContainer)
        doLast {
            val configuration: Configuration = Configuration()
                .withBasedir(project.projectDir.toString())
                .withLogging(Logging.INFO)
                .withJdbc(
                    Jdbc()
                        .withDriver("org.postgresql.Driver")
                        .withUrl("jdbc:postgresql://localhost:5432/shoppy")
                        .withUser("shoppy")
                        .withPassword("shoppy")
                )
                .withGenerator(
                    Generator()
                        .withName("org.jooq.codegen.JavaGenerator")
                        .withDatabase(
                            Database()
                                .withName("org.jooq.meta.postgres.PostgresDatabase")
                                .withIncludes(".*")
                                .withExcludes("DATABASECHANGELOG|DATABASECHANGELOGLOCK")
                                .withInputSchema("public")
                        )
                        .withGenerate(
                            Generate()
                                .withDeprecated(false)
                                .withRecords(true)
                                .withFluentSetters(true)
                                .withDaos(true)
                        )
                        .withTarget(
                            Target()
                                .withDirectory("src/main/java")
                                .withPackageName("ru.chsergeig.shoppy.jooq")
                        )
                        .withStrategy(
                            Strategy()
                                .withName("org.jooq.codegen.DefaultGeneratorStrategy")
                        )
                )
            GenerationTool.generate(configuration)
        }
    }

    compileJava {
        dependsOn(jooqCodeGen)
    }

    jar {
        dependsOn(compileJava)
        enabled = true
    }
}
