rootProject.name = "shoppy"

include(":backend")
include(":db")
include(":frontend")
include(":jooq")

pluginManagement {
    repositories {
        gradlePluginPortal()
        jcenter()
    }
}
