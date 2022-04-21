plugins {
    java
}

val projectVersion: String by rootProject
val projectGroup: String by rootProject

subprojects {
    version = projectVersion
    group = projectGroup
}

val javaProjects = listOf(project(":backend"), project(":jooq"))

configure(javaProjects) {

    apply(plugin = "java")

    configurations.all {
        resolutionStrategy.eachDependency {
            resolutionStrategy.eachDependency {
                when (requested.group) {
                    "org.jooq" -> useVersion("3.16.5")
                    "com.h2database" -> useVersion("2.1.210")
                }
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
    }
}