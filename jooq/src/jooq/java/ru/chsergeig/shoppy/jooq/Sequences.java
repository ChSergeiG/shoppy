/*
 * This file is generated by jOOQ.
 */
package ru.chsergeig.shoppy.jooq;


import org.jooq.Sequence;
import org.jooq.impl.Internal;
import org.jooq.impl.SQLDataType;


/**
 * Convenience access to all sequences in public.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Sequences {

    /**
     * The sequence <code>public.good_id_seq</code>
     */
    public static final Sequence<Integer> GOOD_ID_SEQ = Internal.createSequence("good_id_seq", Public.PUBLIC, SQLDataType.INTEGER.nullable(false), null, null, null, null, false, null);

    /**
     * The sequence <code>public.order_good_good_id_seq</code>
     */
    public static final Sequence<Integer> ORDER_GOOD_GOOD_ID_SEQ = Internal.createSequence("order_good_good_id_seq", Public.PUBLIC, SQLDataType.INTEGER.nullable(false), null, null, null, null, false, null);

    /**
     * The sequence <code>public.order_good_order_id_seq</code>
     */
    public static final Sequence<Integer> ORDER_GOOD_ORDER_ID_SEQ = Internal.createSequence("order_good_order_id_seq", Public.PUBLIC, SQLDataType.INTEGER.nullable(false), null, null, null, null, false, null);

    /**
     * The sequence <code>public.order_id_seq</code>
     */
    public static final Sequence<Integer> ORDER_ID_SEQ = Internal.createSequence("order_id_seq", Public.PUBLIC, SQLDataType.INTEGER.nullable(false), null, null, null, null, false, null);

    /**
     * The sequence <code>public.user_id_seq</code>
     */
    public static final Sequence<Integer> USER_ID_SEQ = Internal.createSequence("user_id_seq", Public.PUBLIC, SQLDataType.INTEGER.nullable(false), null, null, null, null, false, null);

    /**
     * The sequence <code>public.user_order_order_id_seq</code>
     */
    public static final Sequence<Integer> USER_ORDER_ORDER_ID_SEQ = Internal.createSequence("user_order_order_id_seq", Public.PUBLIC, SQLDataType.INTEGER.nullable(false), null, null, null, null, false, null);

    /**
     * The sequence <code>public.user_order_user_id_seq</code>
     */
    public static final Sequence<Integer> USER_ORDER_USER_ID_SEQ = Internal.createSequence("user_order_user_id_seq", Public.PUBLIC, SQLDataType.INTEGER.nullable(false), null, null, null, null, false, null);
}