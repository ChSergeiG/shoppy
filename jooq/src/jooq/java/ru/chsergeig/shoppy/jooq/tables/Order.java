/*
 * This file is generated by jOOQ.
 */
package ru.chsergeig.shoppy.jooq.tables;


import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row3;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import ru.chsergeig.shoppy.jooq.Keys;
import ru.chsergeig.shoppy.jooq.Public;
import ru.chsergeig.shoppy.jooq.enums.Status;
import ru.chsergeig.shoppy.jooq.tables.records.OrderRecord;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Order extends TableImpl<OrderRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.order</code>
     */
    public static final Order ORDER = new Order();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<OrderRecord> getRecordType() {
        return OrderRecord.class;
    }

    /**
     * The column <code>public.order.id</code>.
     */
    public final TableField<OrderRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.order.info</code>.
     */
    public final TableField<OrderRecord, String> INFO = createField(DSL.name("info"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.order.status</code>.
     */
    public final TableField<OrderRecord, Status> STATUS = createField(DSL.name("status"), SQLDataType.VARCHAR.nullable(false).defaultValue(DSL.field("'ADDED'::status", SQLDataType.VARCHAR)).asEnumDataType(ru.chsergeig.shoppy.jooq.enums.Status.class), this, "");

    private Order(Name alias, Table<OrderRecord> aliased) {
        this(alias, aliased, null);
    }

    private Order(Name alias, Table<OrderRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.order</code> table reference
     */
    public Order(String alias) {
        this(DSL.name(alias), ORDER);
    }

    /**
     * Create an aliased <code>public.order</code> table reference
     */
    public Order(Name alias) {
        this(alias, ORDER);
    }

    /**
     * Create a <code>public.order</code> table reference
     */
    public Order() {
        this(DSL.name("order"), null);
    }

    public <O extends Record> Order(Table<O> child, ForeignKey<O, OrderRecord> key) {
        super(child, key, ORDER);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public Identity<OrderRecord, Integer> getIdentity() {
        return (Identity<OrderRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<OrderRecord> getPrimaryKey() {
        return Keys.ORDER_PK;
    }

    @Override
    public List<UniqueKey<OrderRecord>> getKeys() {
        return Arrays.<UniqueKey<OrderRecord>>asList(Keys.ORDER_PK);
    }

    @Override
    public Order as(String alias) {
        return new Order(DSL.name(alias), this);
    }

    @Override
    public Order as(Name alias) {
        return new Order(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Order rename(String name) {
        return new Order(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Order rename(Name name) {
        return new Order(name, null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<Integer, String, Status> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}
