package ru.chsergeig.shoppy.gradle.task

import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.jooq.codegen.GenerationTool
import org.jooq.meta.jaxb.Configuration
import org.jooq.meta.jaxb.Database
import org.jooq.meta.jaxb.Generate
import org.jooq.meta.jaxb.Generator
import org.jooq.meta.jaxb.Jdbc
import org.jooq.meta.jaxb.Logging
import org.jooq.meta.jaxb.Strategy
import org.jooq.meta.jaxb.Target

@CacheableTask
open class JooqGenerateClasses : DefaultTask() {

    @Input
    val jdbcPassword: Property<String> = project.objects.property(String::class.java)

    @Input
    val jdbcUrl: Property<String> = project.objects.property(String::class.java)

    @Input
    val jdbcUsername: Property<String> = project.objects.property(String::class.java)

    @Input
    val targetDirectory: Property<String> = project.objects.property(String::class.java)

    @Input
    val targetPackage: Property<String> = project.objects.property(String::class.java)

    @TaskAction
    fun generate() {
        val configuration: Configuration = Configuration()
            .withBasedir(project.projectDir.toString())
            .withLogging(Logging.INFO)
            .withJdbc(
                Jdbc()
                    .withDriver("org.postgresql.Driver")
                    .withUrl(jdbcUrl.get())
                    .withUser(jdbcUsername.get())
                    .withPassword(jdbcPassword.get())
            )
            .withGenerator(
                Generator()
                    .withName("org.jooq.codegen.JavaGenerator")
                    .withDatabase(
                        Database()
                            .withName("org.jooq.meta.postgres.PostgresDatabase")
                            .withIncludes(".*")
                            .withExcludes("DATABASECHANGELOG|DATABASECHANGELOGLOCK")
                            .withInputSchema("public")
                    )
                    .withGenerate(
                        Generate()
                            .withDeprecated(false)
                            .withRecords(true)
                            .withFluentSetters(true)
                            .withDaos(true)
                    )
                    .withTarget(
                        Target()
                            .withDirectory(targetDirectory.get())
                            .withPackageName(targetPackage.get())
                    )
                    .withStrategy(
                        Strategy()
                            .withName("org.jooq.codegen.DefaultGeneratorStrategy")
                    )
            )
        GenerationTool.generate(configuration)
    }
}