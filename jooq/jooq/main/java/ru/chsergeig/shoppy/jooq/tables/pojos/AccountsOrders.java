/*
 * This file is generated by jOOQ.
 */
package ru.chsergeig.shoppy.jooq.tables.pojos;


import java.io.Serializable;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AccountsOrders implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer accountId;
    private Integer orderId;

    public AccountsOrders() {}

    public AccountsOrders(AccountsOrders value) {
        this.accountId = value.accountId;
        this.orderId = value.orderId;
    }

    public AccountsOrders(
        Integer accountId,
        Integer orderId
    ) {
        this.accountId = accountId;
        this.orderId = orderId;
    }

    /**
     * Getter for <code>public.accounts_orders.account_id</code>. User id
     */
    public Integer getAccountId() {
        return this.accountId;
    }

    /**
     * Setter for <code>public.accounts_orders.account_id</code>. User id
     */
    public AccountsOrders setAccountId(Integer accountId) {
        this.accountId = accountId;
        return this;
    }

    /**
     * Getter for <code>public.accounts_orders.order_id</code>. Order id
     */
    public Integer getOrderId() {
        return this.orderId;
    }

    /**
     * Setter for <code>public.accounts_orders.order_id</code>. Order id
     */
    public AccountsOrders setOrderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("AccountsOrders (");

        sb.append(accountId);
        sb.append(", ").append(orderId);

        sb.append(")");
        return sb.toString();
    }
}
