plugins {
    kotlin("jvm") version "1.8.21"
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation(
        group = "org.springframework.boot",
        name = "org.springframework.boot.gradle.plugin",
        version = "2.6.4"
    )
    implementation(
        group = "io.spring.dependency-management",
        name = "io.spring.dependency-management.gradle.plugin",
        version = "1.0.11.RELEASE"
    )
    implementation(
        group = "org.jetbrains.kotlin",
        name = "kotlin-gradle-plugin",
        version = "1.8.21"
    )
    implementation(
        group = "org.jetbrains.kotlin.kapt",
        name = "org.jetbrains.kotlin.kapt.gradle.plugin",
        version = "1.8.21"
    )
    implementation(
        group = "org.jetbrains.kotlin.plugin.lombok",
        name = "org.jetbrains.kotlin.plugin.lombok.gradle.plugin",
        version = "1.8.21"
    )
    implementation(
        group = "io.freefair.lombok",
        name = "io.freefair.lombok.gradle.plugin",
        version = "5.3.0"
    )
}