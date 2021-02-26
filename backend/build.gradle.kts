val posgresVersion: String by rootProject

plugins {
    java
    id("org.springframework.boot") version "2.4.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("io.freefair.lombok") version "5.3.0"
}

dependencies {
    implementation(project(":jooq"))

    annotationProcessor(group = "org.springframework.boot", name = "spring-boot-configuration-processor")

    implementation(group = "org.springframework.boot", name = "spring-boot-starter")
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-actuator")
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-jooq")
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-web")

    implementation(group = "org.postgresql", name = "postgresql", version = posgresVersion)

    annotationProcessor(group = "org.mapstruct", name = "mapstruct-processor", version = "1.4.2.Final")
    implementation(group = "org.mapstruct", name = "mapstruct", version = "1.4.2.Final")

    testImplementation(group = "org.springframework.boot", name = "spring-boot-starter-test")
    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter", version = "5.5.2")
}

java {
    sourceCompatibility = JavaVersion.VERSION_15
}

tasks {
    test {
        useJUnitPlatform()
    }
}
