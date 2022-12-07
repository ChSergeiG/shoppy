package ru.chsergeig.shoppy.service.common

import ru.chsergeig.shoppy.model.CommonGoodDto
import ru.chsergeig.shoppy.model.ExtendedOrderDto

interface CommonOrdersService {

    fun createOrder(
        goods: List<CommonGoodDto?>?,
        username: String?
    ): String

    fun getOrderByGuid(
        guid: String?,
        username: String?
    ): ExtendedOrderDto
}
