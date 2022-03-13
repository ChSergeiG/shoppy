val projectVersion: String by rootProject
val projectGroup: String by rootProject

configure(subprojects) {
    version = projectVersion
    group = projectGroup

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
        jcenter()
        mavenCentral()
        google()
    }
}
