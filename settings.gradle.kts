rootProject.name = "shoppy"

include(":backend")
include(":frontend")
include(":jooq")
include(":openapi")

pluginManagement {
    repositories {
        gradlePluginPortal()
    }
}
