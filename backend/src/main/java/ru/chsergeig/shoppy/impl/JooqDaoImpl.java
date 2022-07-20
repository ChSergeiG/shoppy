package ru.chsergeig.shoppy.impl;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import ru.chsergeig.shoppy.dao.JooqDao;

public class JooqDaoImpl
        implements JooqDao {

    private final Configuration configuration;

    public JooqDaoImpl(Configuration configuration) {
        this.configuration = configuration;
    }

    public DSLContext ctx() {
        return configuration().dsl();
    }

    @Override
    public Configuration configuration() {
        return configuration;
    }

}
