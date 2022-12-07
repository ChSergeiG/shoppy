import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {
    base
    id("org.openapi.generator") version "6.2.1"
}

val packageToGenerate = "ru.chsergeig.shoppy"

tasks {

    val generateRestApi = register<GenerateTask>("generateRestApi") {
        group = "openapi tools"

        generatorName.set("kotlin-spring")
        inputSpec.set("$projectDir/spec.yml")
        outputDir.set("$rootDir/backend/build/generated/openapi")
        apiPackage.set("$packageToGenerate.api")
        invokerPackage.set("$packageToGenerate.invoker")
        modelPackage.set("$packageToGenerate.model")
        templateDir.set("$projectDir/templates")
        configOptions.putAll(
            mapOf(
                "dateLibrary" to "java8"
            )
        )
        globalProperties.putAll(
            mapOf(
                "modelDocs" to "false",
                "interfaceOnly" to "true"
            )
        )
    }

    val generateJsClient = register<GenerateTask>("generateJsClient") {
        group = "openapi tools"

        generatorName.set("typescript")
        inputSpec.set("$projectDir/spec.yml")
        outputDir.set("$rootDir/frontend/openapi")
    }

    val moveJsClient = register<Copy>("moveJsClient") {
        dependsOn(generateJsClient)
        from("$rootDir/frontend/openapi/models")
        into("$rootDir/frontend/src/types")
        filter { line ->
            when {
                line.contains("import { HttpFile }") -> "// $line"
                line.startsWith("import { ") && line.contains("models") -> line
                    .replace("../models", ".")
                    .replace("import ", "import type ")
                else -> line
            }
        }
    }

    val cleanupGeneratedApiItems = register<Delete>("cleanupGeneratedApiItems") {
        dependsOn(generateRestApi, moveJsClient)
        delete(
            "$rootDir/backend/build/generated/openapi/src/test",
            "$rootDir/backend/build/generated/openapi/src/main/kotlin/org",
            "$rootDir/backend/build/generated/openapi/src/main/kotlin/ru/chsergeig/shoppy/invoker",
            "$rootDir/frontend/openapi",
            "$rootDir/frontend/src/types/all.ts",
            "$rootDir/frontend/src/types/AdminCountedGoodDtoAllOf.ts",
            "$rootDir/frontend/src/types/AdminGoodDtoAllOf.ts",
            "$rootDir/frontend/src/types/AdminOrderDtoAllOf.ts",
            "$rootDir/frontend/src/types/ExtendedOrderDtoAllOf.ts",
            "$rootDir/frontend/src/types/GetAccountsInAdminAreaByOrderIds200ResponseInnerAllOf.ts",
            "$rootDir/frontend/src/types/all.ts",
            "$rootDir/frontend/src/types/ObjectSerializer.ts"
        )
    }

    build {
        dependsOn(cleanupGeneratedApiItems)
    }
}
