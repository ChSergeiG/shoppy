package ru.chsergeig.shoppy.service.admin

import ru.chsergeig.shoppy.model.AdminAccountDto
import ru.chsergeig.shoppy.model.AdminCountedGoodDto
import ru.chsergeig.shoppy.model.AdminOrderAccountsDto
import ru.chsergeig.shoppy.model.AdminOrderDto
import ru.chsergeig.shoppy.model.AdminOrderGoodsDto

interface AdminOrderService {

    fun getAllOrders(): List<AdminOrderDto?>

    fun getOrderById(
        id: Long?
    ): AdminOrderDto

    fun addOrder(
        info: String?
    ): AdminOrderDto

    fun addOrder(
        dto: AdminOrderDto?
    ): AdminOrderDto

    fun updateOrder(
        dto: AdminOrderDto?
    ): AdminOrderDto

    fun deleteOrder(
        id: Int?
    ): Int

    fun getAccountsByOrderId(
        id: Int?
    ): List<AdminAccountDto>

    fun getAccountsByOrderIds(
        requestBody: List<Int>
    ): List<AdminOrderAccountsDto>

    fun getGoodsByOrderId(
        id: Int?
    ): List<AdminCountedGoodDto>

    fun getGoodsByOrderIds(
        requestBody: List<Int>
    ): List<AdminOrderGoodsDto>
}
