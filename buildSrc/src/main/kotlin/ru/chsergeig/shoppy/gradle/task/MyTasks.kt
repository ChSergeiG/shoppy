package ru.chsergeig.shoppy.gradle.task

import com.bmuschko.gradle.docker.tasks.container.DockerCreateContainer
import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.provider.Property
import org.gradle.api.tasks.TaskProvider
import org.jooq.codegen.DefaultGeneratorStrategy
import org.jooq.codegen.JavaGenerator
import org.jooq.meta.postgres.PostgresDatabase
import org.postgresql.Driver

inline fun <reified T : Task> createPostgresContainerConfig(
    imageID: Property<String>,
    vararg dependsOnTasks: TaskProvider<T>
): DockerCreateContainer.() -> Unit = {
    dependsOn(*dependsOnTasks)
    onlyIf { dependsOnTasks.any { it.get().enabled } }
//    doCache(project, this)
    image.set("postgres:13.2")
    imageId.set(imageID)
    hostConfig.portBindings.add("5433:5432")
    withEnvVar("POSTGRES_DB", "shoppy")
    withEnvVar("POSTGRES_USER", "shoppy")
    withEnvVar("POSTGRES_PASSWORD", "shoppy")
    healthCheck.cmd("pg_isready -U shoppy")
    hostConfig.autoRemove.set(true)
}

fun jooqCodeGenConfig(
    finalizedBy: TaskProvider<out Task>,
    vararg dependsOn: TaskProvider<out Task>
): JooqGenerateClasses.() -> Unit = {
    finalizedBy(finalizedBy)
    dependsOn(*dependsOn)
//    doCache(project, this)
    jdbcDriver.set(Driver::class.java.name)
    jdbcUrl.set("jdbc:postgresql://localhost:5433/shoppy")
    jdbcUsername.set("shoppy")
    jdbcPassword.set("shoppy")
    generatorName.set(JavaGenerator::class.java.name)
    generatorDatabaseName.set(PostgresDatabase::class.java.name)
    generatorInputSchema.set("public")
    strategyName.set(DefaultGeneratorStrategy::class.java.name)
    targetDirectory.set("src/main/java")
    targetPackage.set("ru.chsergeig.shoppy.jooq")
}


fun doCache(project: Project, task: DefaultTask) {
    task.inputs.dir(
        project.projectDir.resolve("src/main/resources/liquibase")
    )
    task.outputs.dir(
        project.projectDir.resolve("src/main/java")
    )
}
