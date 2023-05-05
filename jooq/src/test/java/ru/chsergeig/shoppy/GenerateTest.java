package ru.chsergeig.shoppy;

import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import org.jooq.codegen.GenerationTool;
import org.jooq.codegen.JavaGenerator;
import org.jooq.meta.jaxb.Configuration;
import org.jooq.meta.jaxb.Database;
import org.jooq.meta.jaxb.Generate;
import org.jooq.meta.jaxb.Generator;
import org.jooq.meta.jaxb.Jdbc;
import org.jooq.meta.jaxb.SchemaMappingType;
import org.jooq.meta.jaxb.Target;
import org.jooq.meta.postgres.PostgresDatabase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.util.FileSystemUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.apache.commons.io.FileUtils;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Testcontainers
class GenerateTest {

    @Container
    private final PostgreSQLContainer<?> container = new PostgreSQLContainer<>(
            DockerImageName.parse("postgres:13.2")
    );

    @Test
    @EnabledIfSystemProperty(named = "jooqGenerate", matches = "true")
    void generate() throws Exception {
        liquibaseIt();
        jooqIt();
    }

    private void liquibaseIt() throws LiquibaseException {
        final DataSource dataSource = DataSourceBuilder.create()
                .driverClassName(container.getDriverClassName())
                .type(PGSimpleDataSource.class)
                .url(container.getJdbcUrl())
                .username(container.getUsername())
                .password(container.getPassword())
                .build();
        final SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath:/liquibase/main.xml");
        liquibase.setDefaultSchema("public");
        liquibase.setResourceLoader(new DefaultResourceLoader(getClass().getClassLoader()));
        liquibase.afterPropertiesSet();
    }

    private void jooqIt() throws Exception {
        final String packagePath = getClass().getPackageName().replace(".", "/");
        final Path targetDir = Paths.get(
                System.getProperty("user.dir"),
                "jooq/main/java"
        ).normalize();
        final Path tempDir = Files.createTempDirectory("jooq").normalize();
        Files.createDirectories(targetDir);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> FileUtils.deleteQuietly(tempDir.toFile())));
        Configuration configuration = new Configuration()
                .withJdbc(
                        new Jdbc()
                                .withDriver(container.getDriverClassName())
                                .withUrl(container.getJdbcUrl())
                                .withUser(container.getUsername())
                                .withPassword(container.getPassword())
                )

                .withGenerator(
                        new Generator()
                                .withName(JavaGenerator.class.getCanonicalName())
                                .withDatabase(
                                        new Database()
                                                .withName(PostgresDatabase.class.getCanonicalName())
                                                .withIncludes(".*")
                                                .withExcludes("databasechangelog.*")
                                                .withSchemata(
                                                        new SchemaMappingType()
                                                                .withInputSchema("public")
                                                )
                                )
                                .withGenerate(
                                        new Generate()
                                                .withDeprecated(false)
                                                .withRecords(true)
                                                .withFluentSetters(true)
                                                .withDaos(true)
                                )
                                .withTarget(
                                        new Target()
                                                .withPackageName(getClass().getPackageName() + ".jooq")
                                                .withDirectory(tempDir.toString())
                                )
                );
        GenerationTool.generate(configuration);
        FileSystemUtils.deleteRecursively(targetDir);
        Files.move(tempDir, targetDir);
    }


}
