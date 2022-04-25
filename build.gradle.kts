plugins {
    java
    jacoco
}

val projectVersion: String by rootProject
val projectGroup: String by rootProject

jacoco {
    toolVersion = "0.8.7"
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

        testImplementation(group = "org.junit.jupiter", name = "junit-jupiter", version = "5.5.2")
    }

    tasks {
        withType<Test> {
            systemProperty("spring.profiles.active", "test")
            useJUnitPlatform()
        }
    }
}

tasks {

    test {
        dependsOn(javaProjects.flatMap { it.tasks.withType<Test>() })
    }

}
