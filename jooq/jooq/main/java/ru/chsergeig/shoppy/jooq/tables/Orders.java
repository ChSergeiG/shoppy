/*
 * This file is generated by jOOQ.
 */
package ru.chsergeig.shoppy.jooq.tables;


import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row4;
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
import ru.chsergeig.shoppy.jooq.tables.records.OrdersRecord;

import java.util.Arrays;
import java.util.List;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Orders extends TableImpl<OrdersRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.orders</code>
     */
    public static final Orders ORDERS = new Orders();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<OrdersRecord> getRecordType() {
        return OrdersRecord.class;
    }

    /**
     * The column <code>public.orders.id</code>.
     */
    public final TableField<OrdersRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.orders.info</code>. Info about order
     */
    public final TableField<OrdersRecord, String> INFO = createField(DSL.name("info"), SQLDataType.VARCHAR(2048).defaultValue(DSL.field("''::character varying", SQLDataType.VARCHAR)), this, "Info about order");

    /**
     * The column <code>public.orders.status</code>. Order status
     */
    public final TableField<OrdersRecord, Status> STATUS = createField(DSL.name("status"), SQLDataType.VARCHAR.nullable(false).defaultValue(DSL.field("'ADDED'::status", SQLDataType.VARCHAR)).asEnumDataType(ru.chsergeig.shoppy.jooq.enums.Status.class), this, "Order status");

    /**
     * The column <code>public.orders.guid</code>. Unique order guid
     */
    public final TableField<OrdersRecord, String> GUID = createField(DSL.name("guid"), SQLDataType.VARCHAR(36).defaultValue(DSL.field("NULL::character varying", SQLDataType.VARCHAR)), this, "Unique order guid");

    private Orders(Name alias, Table<OrdersRecord> aliased) {
        this(alias, aliased, null);
    }

    private Orders(Name alias, Table<OrdersRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.orders</code> table reference
     */
    public Orders(String alias) {
        this(DSL.name(alias), ORDERS);
    }

    /**
     * Create an aliased <code>public.orders</code> table reference
     */
    public Orders(Name alias) {
        this(alias, ORDERS);
    }

    /**
     * Create a <code>public.orders</code> table reference
     */
    public Orders() {
        this(DSL.name("orders"), null);
    }

    public <O extends Record> Orders(Table<O> child, ForeignKey<O, OrdersRecord> key) {
        super(child, key, ORDERS);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public Identity<OrdersRecord, Integer> getIdentity() {
        return (Identity<OrdersRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<OrdersRecord> getPrimaryKey() {
        return Keys.ORDER_PK;
    }

    @Override
    public List<UniqueKey<OrdersRecord>> getKeys() {
        return Arrays.<UniqueKey<OrdersRecord>>asList(Keys.ORDER_PK);
    }

    @Override
    public Orders as(String alias) {
        return new Orders(DSL.name(alias), this);
    }

    @Override
    public Orders as(Name alias) {
        return new Orders(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Orders rename(String name) {
        return new Orders(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Orders rename(Name name) {
        return new Orders(name, null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<Integer, String, Status, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }
}
