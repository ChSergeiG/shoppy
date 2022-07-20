package ru.chsergeig.shoppy.service.common

import ru.chsergeig.shoppy.dto.ExtendedOrderDto
import ru.chsergeig.shoppy.dto.admin.GoodDto

interface CommonOrdersService {

    fun createOrder(
        goods: List<GoodDto?>?,
        username: String?
    ): String

    fun getOrderByGuid(
        guid: String?,
        username: String?
    ): ExtendedOrderDto
}
