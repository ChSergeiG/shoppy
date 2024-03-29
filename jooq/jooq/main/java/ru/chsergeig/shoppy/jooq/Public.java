/*
 * This file is generated by jOOQ.
 */
package ru.chsergeig.shoppy.jooq;


import org.jooq.Catalog;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;
import ru.chsergeig.shoppy.jooq.tables.Accounts;
import ru.chsergeig.shoppy.jooq.tables.AccountsOrders;
import ru.chsergeig.shoppy.jooq.tables.AccountsRoles;
import ru.chsergeig.shoppy.jooq.tables.Goods;
import ru.chsergeig.shoppy.jooq.tables.JwtTokens;
import ru.chsergeig.shoppy.jooq.tables.Orders;
import ru.chsergeig.shoppy.jooq.tables.OrdersGoods;

import java.util.Arrays;
import java.util.List;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Public extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public</code>
     */
    public static final Public PUBLIC = new Public();

    /**
     * The table <code>public.accounts</code>.
     */
    public final Accounts ACCOUNTS = Accounts.ACCOUNTS;

    /**
     * The table <code>public.accounts_orders</code>.
     */
    public final AccountsOrders ACCOUNTS_ORDERS = AccountsOrders.ACCOUNTS_ORDERS;

    /**
     * The table <code>public.accounts_roles</code>.
     */
    public final AccountsRoles ACCOUNTS_ROLES = AccountsRoles.ACCOUNTS_ROLES;

    /**
     * The table <code>public.goods</code>.
     */
    public final Goods GOODS = Goods.GOODS;

    /**
     * The table <code>public.jwt_tokens</code>.
     */
    public final JwtTokens JWT_TOKENS = JwtTokens.JWT_TOKENS;

    /**
     * The table <code>public.orders</code>.
     */
    public final Orders ORDERS = Orders.ORDERS;

    /**
     * The table <code>public.orders_goods</code>.
     */
    public final OrdersGoods ORDERS_GOODS = OrdersGoods.ORDERS_GOODS;

    /**
     * No further instances allowed
     */
    private Public() {
        super("public", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Sequence<?>> getSequences() {
        return Arrays.<Sequence<?>>asList(
            Sequences.ACCOUNTS_ID_SEQ,
            Sequences.ACCOUNTS_ORDERS_ACCOUNT_ID_SEQ,
            Sequences.ACCOUNTS_ORDERS_ORDER_ID_SEQ,
            Sequences.ACCOUNTS_ROLES_ACCOUNT_ID_SEQ,
            Sequences.GOODS_ID_SEQ,
            Sequences.ORDERS_GOODS_GOOD_ID_SEQ,
            Sequences.ORDERS_GOODS_ORDER_ID_SEQ,
            Sequences.ORDERS_ID_SEQ);
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.<Table<?>>asList(
            Accounts.ACCOUNTS,
            AccountsOrders.ACCOUNTS_ORDERS,
            AccountsRoles.ACCOUNTS_ROLES,
            Goods.GOODS,
            JwtTokens.JWT_TOKENS,
            Orders.ORDERS,
            OrdersGoods.ORDERS_GOODS);
    }
}
