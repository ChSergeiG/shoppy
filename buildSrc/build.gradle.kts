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
}