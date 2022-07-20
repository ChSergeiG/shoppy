package ru.chsergeig.shoppy.dao

import org.jooq.DAO
import org.jooq.Record2
import ru.chsergeig.shoppy.jooq.tables.pojos.Accounts
import ru.chsergeig.shoppy.jooq.tables.pojos.AccountsOrders
import ru.chsergeig.shoppy.jooq.tables.records.AccountsOrdersRecord

interface AccountOrderRepository :
    DAO<AccountsOrdersRecord?, AccountsOrders?, Record2<Int?, Int?>> {

    fun getAccountsByOrderId(
        orderId: Int?
    ): List<Accounts>
}
