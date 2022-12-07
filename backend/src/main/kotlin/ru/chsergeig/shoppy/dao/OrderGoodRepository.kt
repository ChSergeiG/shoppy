package ru.chsergeig.shoppy.dao

import org.jooq.DAO
import org.jooq.Record2
import org.jooq.Record7
import org.jooq.Result
import ru.chsergeig.shoppy.jooq.enums.Status
import ru.chsergeig.shoppy.jooq.tables.pojos.Goods
import ru.chsergeig.shoppy.jooq.tables.pojos.OrdersGoods
import ru.chsergeig.shoppy.jooq.tables.records.OrdersGoodsRecord
import java.math.BigDecimal

interface OrderGoodRepository :
    DAO<OrdersGoodsRecord, OrdersGoods, Record2<Int?, Int?>> {

    fun getGoodsByOrderId(
        orderId: Int?
    ): Map<Goods, Long>

    fun getAccountsByOrderIds(
        orderIds: List<Int>
    ): Result<Record2<String, Int>>

    fun getGoodsByOrderIds(
        orderIds: List<Int>
    ): Result<Record7<Int, Long, Int, String, String, BigDecimal, Status>>
}
