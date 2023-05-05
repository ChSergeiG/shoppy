import ru.chsergeig.shoppy.gradle.JAVA_COMPATIBILITY
import ru.chsergeig.shoppy.gradle.JOOQ_VERSION
import ru.chsergeig.shoppy.gradle.LIQUIBASE_VERSION
import ru.chsergeig.shoppy.gradle.POSTGRES_VERSION
import ru.chsergeig.shoppy.gradle.TESTCONTAINERS_VERSION

plugins {
    `java-library`
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

spotless {
    java {
        targetExclude("jooq")
    }
}

dependencies {
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-jooq")
    implementation(group = "org.postgresql", name = "postgresql", version = POSTGRES_VERSION)
    api(group = "org.jooq", name = "jooq-codegen", version = JOOQ_VERSION)
    api(group = "org.jooq", name = "jooq-meta", version = JOOQ_VERSION)
    testImplementation(group = "org.liquibase", name = "liquibase-core", version = LIQUIBASE_VERSION)
    testImplementation(group = "org.testcontainers", name = "junit-jupiter", version = TESTCONTAINERS_VERSION)
    testImplementation(group = "org.testcontainers", name = "postgresql", version = TESTCONTAINERS_VERSION)
    testImplementation(group = "org.springframework.boot", name = "spring-boot")
}

java {
    sourceCompatibility = JAVA_COMPATIBILITY
}

sourceSets {
    main {
        java {
            srcDirs("jooq/main/java")
        }
    }
}

tasks {
    test {
        useJUnitPlatform()
        systemProperty("jooqGenerate", System.getProperty("jooqGenerate") ?: "")
    }
}


