@file:Suppress("UnstableApiUsage")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import ru.chsergeig.shoppy.gradle.JAVA_COMPATIBILITY

plugins {
    java
    jacoco
    id("com.diffplug.spotless") version "6.8.0"
}

val projectVersion: String by rootProject
val projectGroup: String by rootProject

spotless {
    kotlinGradle {
        target("*.gradle.kts")
        ktlint()
    }
}

jacoco {
    toolVersion = "0.8.7"
}

repositories {
    mavenLocal()
    mavenCentral()
}

subprojects {
    version = projectVersion
    group = projectGroup

    apply(plugin = "com.diffplug.spotless")
}

val javaProjects = listOf(project(":backend"), project(":jooq"))
val kotlinProjects = listOf(project(":backend"))

configure(javaProjects) {
    apply(plugin = "jacoco")
    apply(plugin = "java")

    spotless {
        java {
            importOrder("", "javax", "java", "\\#")
            removeUnusedImports()
        }
    }

    java {
        sourceCompatibility = JAVA_COMPATIBILITY
        targetCompatibility = JAVA_COMPATIBILITY
    }

    configurations.all {
        resolutionStrategy.eachDependency {
            when (requested.group) {
                "org.jooq" -> useVersion("3.16.5")
                "com.h2database" -> useVersion("2.1.210")
            }
        }
    }

    repositories {
        mavenLocal()
        mavenCentral()
        google()
    }

    dependencies {
        implementation(group = "org.jetbrains", name = "annotations", version = "23.0.0")

        testImplementation(group = "com.h2database", name = "h2", version = "2.1.212")
        testImplementation(group = "org.junit.jupiter", name = "junit-jupiter", version = "5.5.2")
    }

    tasks {
        withType<JavaCompile> {
            sourceCompatibility = "$JAVA_COMPATIBILITY"
            targetCompatibility = "$JAVA_COMPATIBILITY"
        }

        withType<Test> {
            systemProperty("spring.profiles.active", "test")
            useJUnitPlatform()
        }
    }
}

configure(kotlinProjects) {
    apply(plugin = "jacoco")
    apply(plugin = "org.jetbrains.kotlin.jvm")

    spotless {
        kotlin {
            ktlint()
        }
    }

    tasks {
        withType<KotlinCompile> {
            kotlinOptions {
                jvmTarget = "$JAVA_COMPATIBILITY"
            }
        }
    }
}

val coverageExclusions = listOf(
    "**/Application**",
    "**/dao/**",
    "**/dto/**",
    "**/jooq/**"
)

tasks {

    test {
        dependsOn(javaProjects.flatMap { it.tasks.withType<Test>() })
    }

    register<JacocoReport>("jacocoRootReport") {
        dependsOn(test)
        additionalSourceDirs.setFrom(files(javaProjects.flatMap { it.sourceSets.main.get().allSource.srcDirs }))
        sourceDirectories.setFrom(files(javaProjects.flatMap { it.sourceSets.main.get().allSource.srcDirs }))
        executionData.setFrom(files(javaProjects.flatMap { it.tasks.jacocoTestReport.get().executionData }))
        classDirectories.setFrom(
            javaProjects.map {
                it.files(
                    it.fileTree(it.buildDir.resolve("classes/java/main")).exclude(coverageExclusions),
                    it.fileTree(it.buildDir.resolve("classes/kotlin/main")).exclude(coverageExclusions)
                )
            }
        )
        reports {
            html.required.set(true)
            xml.required.set(true)
            csv.required.set(false)
        }
    }
}
