import nu.studer.gradle.jooq.JooqEdition
import org.jooq.meta.jaxb.Logging

plugins {
    java
    id("nu.studer.jooq") version "5.2.1"
    id("org.springframework.boot") version "2.4.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
}

dependencies {
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-jooq")
    jooqGenerator(group = "org.postgresql", name = "postgresql", version = "42.2.14")
}

java {
    sourceCompatibility = JavaVersion.VERSION_15
}

jooq {
    version.set("3.14.7")
    edition.set(JooqEdition.OSS)
    configurations {
        create("main") {
            generateSchemaSourceOnCompilation.set(true)
            jooqConfiguration.apply {
                logging = Logging.WARN
                jdbc.apply {
                    driver = "org.postgresql.Driver"
                    url = "jdbc:postgresql://localhost:5432/shoppy"
                    user = "shoppy"
                    password = "shoppy"
                }
                generator.apply {
                    name = "org.jooq.codegen.DefaultGenerator"
                    database.apply {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        inputSchema = "public"
                    }
                    generate.apply {
                        isDeprecated = false
                        isRecords = true
                        isImmutablePojos = true
                        isFluentSetters = true
                    }
                    target.apply {
                        packageName = "ru.chsergeig.shoppy.jooq"
                        directory = "src/main/java"
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
