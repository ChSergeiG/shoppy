rootProject.name = "shoppy"

include(":backend")
include(":frontend")
include(":jooq")

pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}
