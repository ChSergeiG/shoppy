package ru.chsergeig.shoppy.service.admin

import ru.chsergeig.shoppy.dto.admin.AccountDto
import ru.chsergeig.shoppy.dto.admin.CountedGoodDto
import ru.chsergeig.shoppy.dto.admin.OrderDto

interface AdminOrderService {

    fun getAllOrders(): List<OrderDto?>

    fun getOrderById(
        id: Long?
    ): OrderDto

    fun addOrder(
        info: String?
    ): OrderDto

    fun addOrder(
        dto: OrderDto?
    ): OrderDto

    fun updateOrder(
        dto: OrderDto?
    ): OrderDto

    fun deleteOrder(
        id: Int?
    ): Int

    fun getAccountsByOrderId(
        id: Int?
    ): List<AccountDto>

    fun getGoodsByOrderId(
        id: Int?
    ): List<CountedGoodDto>
}
