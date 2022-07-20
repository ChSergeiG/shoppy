package ru.chsergeig.shoppy.dao

import org.jooq.DAO
import ru.chsergeig.shoppy.jooq.tables.pojos.Orders
import ru.chsergeig.shoppy.jooq.tables.records.OrdersRecord

interface OrderRepository : DAO<OrdersRecord, Orders, Int>
