import ru.chsergeig.shoppy.gradle.LIQUIBASE_VERSION
import ru.chsergeig.shoppy.gradle.MAPSTRUCT_VERSION
import ru.chsergeig.shoppy.gradle.POSTGRES_VERSION
import ru.chsergeig.shoppy.gradle.SOURCE_COMPATIBILITY

plugins {
    java
    id("org.springframework.boot") version "2.6.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("io.freefair.lombok") version "6.4.1"
}

sourceSets {
    main {
        resources {
            srcDirs.add(file("${rootProject.projectDir}/jooq/src/main/resources"))
        }
    }
}

dependencies {
    implementation(project(":jooq"))

    annotationProcessor(group = "org.springframework.boot", name = "spring-boot-configuration-processor")
    annotationProcessor(group = "org.mapstruct", name = "mapstruct-processor", version = MAPSTRUCT_VERSION)

    implementation(group = "org.springframework.boot", name = "spring-boot-starter")
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-actuator")
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-jooq")
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-security")
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-web")
    implementation(group = "org.springframework.data", name = "spring-data-commons")

    testImplementation(group = "org.springframework.boot", name = "spring-boot-starter-test")

    implementation(group = "io.jsonwebtoken", name = "jjwt", version = "0.9.1")
    implementation(group = "io.springfox", name = "springfox-boot-starter", version = "3.0.0")
    implementation(group = "io.springfox", name = "springfox-swagger-ui", version = "3.0.0")

    implementation(group = "org.liquibase", name = "liquibase-core", version = LIQUIBASE_VERSION)
    implementation(group = "org.mapstruct", name = "mapstruct", version = MAPSTRUCT_VERSION)
    implementation(group = "org.postgresql", name = "postgresql", version = POSTGRES_VERSION)

    testImplementation(group = "io.rest-assured", name = "rest-assured", version = "4.5.1")
    testImplementation(group = "io.rest-assured", name = "json-path", version = "4.5.1")
    testImplementation(group = "io.rest-assured", name = "xml-path", version = "4.5.1")
    testImplementation(group = "io.rest-assured", name = "json-schema-validator", version = "4.5.1")

    testImplementation(group = "org.hamcrest", name = "hamcrest", version = "2.2")
}

java {
    sourceCompatibility = SOURCE_COMPATIBILITY
}

tasks {
    bootJar {
        layered {
            enabled = true
        }
    }
}
