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
import org.jooq.Row2;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import ru.chsergeig.shoppy.jooq.Keys;
import ru.chsergeig.shoppy.jooq.Public;
import ru.chsergeig.shoppy.jooq.tables.records.OrderGoodRecord;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class OrderGood extends TableImpl<OrderGoodRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.order_good</code>
     */
    public static final OrderGood ORDER_GOOD = new OrderGood();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<OrderGoodRecord> getRecordType() {
        return OrderGoodRecord.class;
    }

    /**
     * The column <code>public.order_good.order_id</code>.
     */
    public final TableField<OrderGoodRecord, Integer> ORDER_ID = createField(DSL.name("order_id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.order_good.good_id</code>.
     */
    public final TableField<OrderGoodRecord, Integer> GOOD_ID = createField(DSL.name("good_id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    private OrderGood(Name alias, Table<OrderGoodRecord> aliased) {
        this(alias, aliased, null);
    }

    private OrderGood(Name alias, Table<OrderGoodRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.order_good</code> table reference
     */
    public OrderGood(String alias) {
        this(DSL.name(alias), ORDER_GOOD);
    }

    /**
     * Create an aliased <code>public.order_good</code> table reference
     */
    public OrderGood(Name alias) {
        this(alias, ORDER_GOOD);
    }

    /**
     * Create a <code>public.order_good</code> table reference
     */
    public OrderGood() {
        this(DSL.name("order_good"), null);
    }

    public <O extends Record> OrderGood(Table<O> child, ForeignKey<O, OrderGoodRecord> key) {
        super(child, key, ORDER_GOOD);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public Identity<OrderGoodRecord, Integer> getIdentity() {
        return (Identity<OrderGoodRecord, Integer>) super.getIdentity();
    }

    @Override
    public List<ForeignKey<OrderGoodRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<OrderGoodRecord, ?>>asList(Keys.ORDER_GOOD__ORDER_GOOD_ORDER_ID_FKEY, Keys.ORDER_GOOD__ORDER_GOOD_GOOD_ID_FKEY);
    }

    private transient Order _order;
    private transient Good _good;

    public Order order() {
        if (_order == null)
            _order = new Order(this, Keys.ORDER_GOOD__ORDER_GOOD_ORDER_ID_FKEY);

        return _order;
    }

    public Good good() {
        if (_good == null)
            _good = new Good(this, Keys.ORDER_GOOD__ORDER_GOOD_GOOD_ID_FKEY);

        return _good;
    }

    @Override
    public OrderGood as(String alias) {
        return new OrderGood(DSL.name(alias), this);
    }

    @Override
    public OrderGood as(Name alias) {
        return new OrderGood(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public OrderGood rename(String name) {
        return new OrderGood(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public OrderGood rename(Name name) {
        return new OrderGood(name, null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row2<Integer, Integer> fieldsRow() {
        return (Row2) super.fieldsRow();
    }
}
