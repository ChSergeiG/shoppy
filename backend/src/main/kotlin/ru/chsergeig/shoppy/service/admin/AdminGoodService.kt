package ru.chsergeig.shoppy.service.admin

import ru.chsergeig.shoppy.dto.admin.GoodDto

interface AdminGoodService {

    fun getAllGoods(): List<GoodDto?>

    fun getGoodById(
        id: Long?
    ): GoodDto

    fun addGood(
        name: String?
    ): GoodDto

    fun addGood(
        dto: GoodDto?
    ): GoodDto

    fun updateGood(
        dto: GoodDto?
    ): GoodDto

    fun deleteGood(
        article: String?
    ): Int
}
