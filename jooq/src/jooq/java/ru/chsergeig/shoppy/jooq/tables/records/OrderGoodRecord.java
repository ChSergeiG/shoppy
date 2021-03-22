/*
 * This file is generated by jOOQ.
 */
package ru.chsergeig.shoppy.jooq.tables.records;


import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.TableRecordImpl;

import ru.chsergeig.shoppy.jooq.tables.OrderGood;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class OrderGoodRecord extends TableRecordImpl<OrderGoodRecord> implements Record2<Integer, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.order_good.order_id</code>.
     */
    public OrderGoodRecord setOrderId(Integer value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>public.order_good.order_id</code>.
     */
    public Integer getOrderId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.order_good.good_id</code>.
     */
    public OrderGoodRecord setGoodId(Integer value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>public.order_good.good_id</code>.
     */
    public Integer getGoodId() {
        return (Integer) get(1);
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row2<Integer, Integer> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    public Row2<Integer, Integer> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return OrderGood.ORDER_GOOD.ORDER_ID;
    }

    @Override
    public Field<Integer> field2() {
        return OrderGood.ORDER_GOOD.GOOD_ID;
    }

    @Override
    public Integer component1() {
        return getOrderId();
    }

    @Override
    public Integer component2() {
        return getGoodId();
    }

    @Override
    public Integer value1() {
        return getOrderId();
    }

    @Override
    public Integer value2() {
        return getGoodId();
    }

    @Override
    public OrderGoodRecord value1(Integer value) {
        setOrderId(value);
        return this;
    }

    @Override
    public OrderGoodRecord value2(Integer value) {
        setGoodId(value);
        return this;
    }

    @Override
    public OrderGoodRecord values(Integer value1, Integer value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached OrderGoodRecord
     */
    public OrderGoodRecord() {
        super(OrderGood.ORDER_GOOD);
    }

    /**
     * Create a detached, initialised OrderGoodRecord
     */
    public OrderGoodRecord(Integer orderId, Integer goodId) {
        super(OrderGood.ORDER_GOOD);

        setOrderId(orderId);
        setGoodId(goodId);
    }
}