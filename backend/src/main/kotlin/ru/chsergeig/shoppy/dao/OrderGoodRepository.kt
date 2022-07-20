package ru.chsergeig.shoppy.dao

import org.jooq.DAO
import org.jooq.Record2
import ru.chsergeig.shoppy.jooq.tables.pojos.Goods
import ru.chsergeig.shoppy.jooq.tables.pojos.OrdersGoods
import ru.chsergeig.shoppy.jooq.tables.records.OrdersGoodsRecord

interface OrderGoodRepository :
    DAO<OrdersGoodsRecord, OrdersGoods, Record2<Int?, Int?>> {

    fun getGoodsByOrderId(
        orderId: Int?
    ): Map<Goods, Long>
}
