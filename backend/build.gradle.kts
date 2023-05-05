import ru.chsergeig.shoppy.gradle.LIQUIBASE_VERSION
import ru.chsergeig.shoppy.gradle.MAPSTRUCT_VERSION
import ru.chsergeig.shoppy.gradle.POSTGRES_VERSION
import ru.chsergeig.shoppy.gradle.REST_ASSURED_VERSION

plugins {
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("io.freefair.lombok")
    kotlin("kapt")
    kotlin("plugin.lombok")
}

spotless {
    kotlin {
        targetExclude("**/generated/**/*.*")
    }
    java {
        targetExclude("**/generated/**/*.*")
    }
}

kotlin {
    sourceSets["main"].apply {
        kotlin.srcDir("$buildDir/generated/openapi/src/main/kotlin")
    }
}

kotlinLombok {
    lombokConfigurationFile(file("lombok.config"))
}

kapt {
    keepJavacAnnotationProcessors = true
}

sourceSets {
    main {
        resources {
            srcDirs.add(file("${rootProject.projectDir}/jooq/src/main/resources"))
        }
    }
}

dependencies {
    implementation(project(":annotation-processor"))
    implementation(project(":jooq"))
    implementation(project(":openapi"))

    annotationProcessor(group = "org.springframework.boot", name = "spring-boot-configuration-processor")
    annotationProcessor(group = "org.mapstruct", name = "mapstruct-processor", version = MAPSTRUCT_VERSION)
    kapt(project(":annotation-processor"))

    implementation(group = "org.springframework.boot", name = "spring-boot-starter")
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-actuator")
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-jooq")
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-security")
    implementation(group = "org.springframework.boot", name = "spring-boot-starter-web")
    implementation(group = "org.springframework.data", name = "spring-data-commons")

    testImplementation(group = "org.springframework.boot", name = "spring-boot-starter-test")

    implementation(group = "org.jetbrains.kotlin", name = "kotlin-reflect")

    implementation(group = "io.jsonwebtoken", name = "jjwt", version = "0.9.1")
    implementation(group = "io.springfox", name = "springfox-boot-starter", version = "3.0.0")
    implementation(group = "io.springfox", name = "springfox-swagger-ui", version = "3.0.0")
    implementation(group = "jakarta.annotation", name = "jakarta.annotation-api", version = "2.1.1")
    implementation(group = "jakarta.validation", name = "jakarta.validation-api", version = "3.0.2")
    implementation(group = "javax.annotation", name = "javax.annotation-api", version = "1.3.2")
    implementation(group = "javax.validation", name = "validation-api", version = "2.0.1.Final")

    implementation(group = "com.squareup.moshi", name = "moshi-kotlin", version = "1.13.0")
    implementation(group = "com.squareup.moshi", name = "moshi-adapters", version = "1.13.0")
    implementation(group = "com.squareup.okhttp3", name = "okhttp", version = "4.10.0")

    implementation(group = "org.liquibase", name = "liquibase-core", version = LIQUIBASE_VERSION)
    implementation(group = "org.mapstruct", name = "mapstruct", version = MAPSTRUCT_VERSION)
    implementation(group = "org.postgresql", name = "postgresql", version = POSTGRES_VERSION)

    testImplementation(group = "io.rest-assured", name = "rest-assured", version = REST_ASSURED_VERSION)
    testImplementation(group = "io.rest-assured", name = "json-path", version = REST_ASSURED_VERSION)
    testImplementation(group = "io.rest-assured", name = "xml-path", version = REST_ASSURED_VERSION)
    testImplementation(group = "io.rest-assured", name = "json-schema-validator", version = REST_ASSURED_VERSION)

    testImplementation(group = "org.hamcrest", name = "hamcrest", version = "2.2")
}

tasks {
    compileJava {
        dependsOn(":openapi:build")
    }

    compileKotlin {
        dependsOn(":openapi:build")
    }

    bootJar {
        layered {
            enabled = true
        }
    }
}
