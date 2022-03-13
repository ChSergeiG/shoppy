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
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-web")

    testImplementation(group = "org.springframework.boot", name = "spring-boot-starter-test")

//    implementation(group = "io.springfox", name = "springfox-boot-starter", version = "3.0.0")
//    implementation(group = "io.springfox", name = "springfox-swagger-ui", version = "3.0.0")

    implementation(group = "org.liquibase", name = "liquibase-core", version = LIQUIBASE_VERSION)
    implementation(group = "org.mapstruct", name = "mapstruct", version = MAPSTRUCT_VERSION)
    implementation(group = "org.postgresql", name = "postgresql", version = POSTGRES_VERSION)

    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter", version = "5.5.2")
}

java {
    sourceCompatibility = SOURCE_COMPATIBILITY
}

tasks {
    test {
        useJUnitPlatform()
    }
}
