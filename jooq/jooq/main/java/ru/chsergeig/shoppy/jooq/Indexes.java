/*
 * This file is generated by jOOQ.
 */
package ru.chsergeig.shoppy.jooq;


import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;
import ru.chsergeig.shoppy.jooq.tables.Accounts;


/**
 * A class modelling indexes of tables in public.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index ACCOUNT_ID_UINDEX = Internal.createIndex(DSL.name("account_id_uindex"), Accounts.ACCOUNTS, new OrderField[] { Accounts.ACCOUNTS.ID }, true);
    public static final Index ACCOUNT_LOGIN_UINDEX = Internal.createIndex(DSL.name("account_login_uindex"), Accounts.ACCOUNTS, new OrderField[] { Accounts.ACCOUNTS.LOGIN }, true);
}
