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
     * The sequence <code>public.users_id_seq</code>
     */
    public static final Sequence<Integer> USERS_ID_SEQ = Internal.createSequence("users_id_seq", Public.PUBLIC, SQLDataType.INTEGER.nullable(false), null, null, null, null, false, null);
}
