plugins {
    kotlin("jvm") version "1.6.10"
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation(
        group = "com.bmuschko.docker-remote-api",
        name = "com.bmuschko.docker-remote-api.gradle.plugin",
        version = "7.3.0"
    )
    implementation(group = "jakarta.xml.bind", name = "jakarta.xml.bind-api", version = "3.0.1")
    implementation(group = "org.jooq", name = "jooq-codegen", version = "3.16.5")
    implementation(group = "org.postgresql", name = "postgresql", version = "42.2.18")
}