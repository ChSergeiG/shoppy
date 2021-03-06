/*
 * This file is generated by jOOQ.
 */
package ru.chsergeig.shoppy.jooq.tables.pojos;


import java.io.Serializable;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class UserOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Integer userId;
    private final Integer orderId;

    public UserOrder(UserOrder value) {
        this.userId = value.userId;
        this.orderId = value.orderId;
    }

    public UserOrder(
        Integer userId,
        Integer orderId
    ) {
        this.userId = userId;
        this.orderId = orderId;
    }

    /**
     * Getter for <code>public.user_order.user_id</code>.
     */
    public Integer getUserId() {
        return this.userId;
    }

    /**
     * Getter for <code>public.user_order.order_id</code>.
     */
    public Integer getOrderId() {
        return this.orderId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("UserOrder (");

        sb.append(userId);
        sb.append(", ").append(orderId);

        sb.append(")");
        return sb.toString();
    }
}
