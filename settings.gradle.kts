rootProject.name = "shoppy"

include(":annotation-processor", ":backend", ":frontend", ":jooq", ":openapi")

pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}
