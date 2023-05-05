/*
 * This file is generated by jOOQ.
 */
package ru.chsergeig.shoppy.jooq.tables.records;


import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;
import ru.chsergeig.shoppy.jooq.tables.AccountsOrders;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AccountsOrdersRecord extends UpdatableRecordImpl<AccountsOrdersRecord> implements Record2<Integer, Integer> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.accounts_orders.account_id</code>. User id
     */
    public AccountsOrdersRecord setAccountId(Integer value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>public.accounts_orders.account_id</code>. User id
     */
    public Integer getAccountId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.accounts_orders.order_id</code>. Order id
     */
    public AccountsOrdersRecord setOrderId(Integer value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>public.accounts_orders.order_id</code>. Order id
     */
    public Integer getOrderId() {
        return (Integer) get(1);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record2<Integer, Integer> key() {
        return (Record2) super.key();
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
        return AccountsOrders.ACCOUNTS_ORDERS.ACCOUNT_ID;
    }

    @Override
    public Field<Integer> field2() {
        return AccountsOrders.ACCOUNTS_ORDERS.ORDER_ID;
    }

    @Override
    public Integer component1() {
        return getAccountId();
    }

    @Override
    public Integer component2() {
        return getOrderId();
    }

    @Override
    public Integer value1() {
        return getAccountId();
    }

    @Override
    public Integer value2() {
        return getOrderId();
    }

    @Override
    public AccountsOrdersRecord value1(Integer value) {
        setAccountId(value);
        return this;
    }

    @Override
    public AccountsOrdersRecord value2(Integer value) {
        setOrderId(value);
        return this;
    }

    @Override
    public AccountsOrdersRecord values(Integer value1, Integer value2) {
        value1(value1);
        value2(value2);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached AccountsOrdersRecord
     */
    public AccountsOrdersRecord() {
        super(AccountsOrders.ACCOUNTS_ORDERS);
    }

    /**
     * Create a detached, initialised AccountsOrdersRecord
     */
    public AccountsOrdersRecord(Integer accountId, Integer orderId) {
        super(AccountsOrders.ACCOUNTS_ORDERS);

        setAccountId(accountId);
        setOrderId(orderId);
    }
}