import nu.studer.gradle.jooq.JooqEdition
import org.jooq.meta.jaxb.Logging.WARN
import ru.chsergeig.shoppy.gradle.POSTGRES_VERSION
import ru.chsergeig.shoppy.gradle.SOURCE_COMPATIBILITY

plugins {
    java
    id("nu.studer.jooq") version "7.1.1"
    id("org.springframework.boot") version "2.6.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
}

sourceSets {
    main {
        java {
            srcDirs.add(file("src/jooq"))
        }
    }
}

dependencies {
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-jooq")

    implementation(group = "org.postgresql", name = "postgresql", version = POSTGRES_VERSION)
    jooqGenerator(group = "org.postgresql", name = "postgresql", version = POSTGRES_VERSION)
    implementation(group = "org.liquibase", name = "liquibase-core", version = "3.8.1")
}

java {
    sourceCompatibility = SOURCE_COMPATIBILITY
}

jooq {
    version.set("3.14.7")
    edition.set(JooqEdition.OSS)
    configurations {
        create("main") {
            generateSchemaSourceOnCompilation.set(true)
            jooqConfiguration.apply {
                logging = WARN
                jdbc.apply {
                    driver = "org.postgresql.Driver"
                    url = "jdbc:postgresql://localhost:5432/shoppy"
                    user = "shoppy"
                    password = "shoppy"
                }
                generator.apply {
                    name = "org.jooq.codegen.JavaGenerator"
                    database.apply {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        inputSchema = "public"
                        excludes = """
                            DATABASECHANGELOG
                          | DATABASECHANGELOGLOCK
                        """.trimIndent()
                    }
                    generate.apply {
                        isDeprecated = false
                        isRecords = true
                        isFluentSetters = true
                        isDaos = true
                    }
                    target.apply {
                        packageName = "ru.chsergeig.shoppy.jooq"
                        directory = "src/jooq/java"
                    }
                    strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"
                }
            }
        }
    }
}

tasks {
    bootJar {
        enabled = false
    }
    jar {
        enabled = true
    }
    "generateJooq" {
        dependsOn(clean)
    }
}
