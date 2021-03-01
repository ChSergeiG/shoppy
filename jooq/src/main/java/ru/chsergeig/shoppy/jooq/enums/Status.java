/*
 * This file is generated by jOOQ.
 */
package ru.chsergeig.shoppy.jooq.enums;


import org.jooq.Catalog;
import org.jooq.EnumType;
import org.jooq.Schema;

import ru.chsergeig.shoppy.jooq.Public;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public enum Status implements EnumType {

    ADDED("ADDED"),

    ACTIVE("ACTIVE"),

    REMOVED("REMOVED"),

    DISABLED("DISABLED");

    private final String literal;

    private Status(String literal) {
        this.literal = literal;
    }

    @Override
    public Catalog getCatalog() {
        return getSchema().getCatalog();
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public String getName() {
        return "status";
    }

    @Override
    public String getLiteral() {
        return literal;
    }
}
