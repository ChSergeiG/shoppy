package ru.chsergeig.shoppy.service;

import lombok.RequiredArgsConstructor;
import org.jooq.ExecuteContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListener;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@RequiredArgsConstructor
public class JooqDsl {

    private final DataSource dataSource;

    @Bean(name = "jooqConnectionProvider")
    DataSourceConnectionProvider connectionProvider() {
        return new DataSourceConnectionProvider(new TransactionAwareDataSourceProxy(dataSource));
    }

    @Bean(name = "jooqDslContext")
    DefaultDSLContext dsl() {
        return new DefaultDSLContext(configuration());
    }

    DefaultConfiguration configuration() {
        DefaultConfiguration jooqConfiguration = new DefaultConfiguration();

        jooqConfiguration.set(connectionProvider());
        jooqConfiguration.set(
                new DefaultExecuteListenerProvider(
                        new DefaultExecuteListener() {
                            @Override
                            public void exception(ExecuteContext context) {
                                SQLDialect dialect = context.configuration().dialect();
                                SQLExceptionTranslator translator = new SQLErrorCodeSQLExceptionTranslator(dialect.name());

                                context.exception(translator.translate("Access database using jOOQ", context.sql(), context.sqlException()));
                            }
                        }
                )
        );
        return jooqConfiguration;
    }

}
