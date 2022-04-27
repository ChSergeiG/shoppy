@file:Suppress("UnstableApiUsage")

plugins {
    java
    jacoco
}

val projectVersion: String by rootProject
val projectGroup: String by rootProject

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
}

val javaProjects = listOf(project(":backend"), project(":jooq"))

configure(javaProjects) {

    apply(plugin = "java")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "jacoco")

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
        withType<Test> {
            systemProperty("spring.profiles.active", "test")
            useJUnitPlatform()
        }
    }
}

val coverageExclusions = listOf(
    "**/Application**",
    "**/dao/**",
    "**/dto/**",
    "**/jooq/**",
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

