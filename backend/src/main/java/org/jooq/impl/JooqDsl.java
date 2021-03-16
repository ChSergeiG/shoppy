package org.jooq.impl;

import lombok.RequiredArgsConstructor;
import org.jooq.ExecuteContext;
import org.jooq.SQLDialect;
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
    public DataSourceConnectionProvider connectionProvider() {
        return new DataSourceConnectionProvider(new TransactionAwareDataSourceProxy(dataSource));
    }

    @Bean(name = "jooqDslContext")
    public DefaultDSLContext dsl() {
        return new DefaultDSLContext(configuration());
    }

    private DefaultConfiguration configuration() {
        DefaultConfiguration jooqConfiguration = new DefaultConfiguration(SQLDialect.POSTGRES);

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
