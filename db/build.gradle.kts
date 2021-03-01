val posgresVersion: String by rootProject

plugins {
    id("org.liquibase.gradle") version "2.0.3"
}

dependencies {
    liquibaseRuntime(group = "org.liquibase", name = "liquibase-core", version = "3.8.1")
    liquibaseRuntime(group = "org.postgresql", name = "postgresql", version = posgresVersion)
    liquibaseRuntime(group = "javax.xml.bind", name = "jaxb-api", version = "2.3.1")
}

liquibase {
    activities.register("main") {
        arguments = mapOf(
                "logLevel" to "warn",
                "changeLogFile" to "migrations/main.xml",
                "url" to "jdbc:postgresql://localhost:5432/shoppy",
                "username" to "shoppy",
                "password" to "shoppy"
        )
    }
    runList = "main"
}
