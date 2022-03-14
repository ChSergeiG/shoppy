package ru.chsergeig.shoppy.configuration;

import org.jooq.SQLDialect;
import org.jooq.conf.Settings;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class JooqConfiguration {

    @Value("${spring.jooq.sql-dialect}")
    private String dialect;

    @Bean
    @Primary
    public DefaultDSLContext dsl(
            org.jooq.Configuration configuration
    ) {
        return new DefaultDSLContext(configuration);
    }

    @Bean
    @Primary
    public org.jooq.Configuration configuration(
            DataSourceConnectionProvider dataSourceConnectionProvider
    ) {
        DefaultConfiguration jooqConfiguration = new DefaultConfiguration();
        jooqConfiguration.set(dataSourceConnectionProvider);
        jooqConfiguration.set(new Settings().withRenderSchema(true));
        jooqConfiguration.set(SQLDialect.valueOf(dialect));

        return jooqConfiguration;
    }


}
