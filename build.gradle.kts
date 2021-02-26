val projectVersion: String by rootProject
val projectGroup: String by rootProject

configure(subprojects) {
    version = projectVersion
    group = projectGroup

    repositories {
        mavenLocal()
        jcenter()
        mavenCentral()
        google()
    }
}
