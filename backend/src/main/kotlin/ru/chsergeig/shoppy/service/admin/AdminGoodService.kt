package ru.chsergeig.shoppy.service.admin

import ru.chsergeig.shoppy.model.AdminGoodDto

interface AdminGoodService {

    fun getAllGoods(): List<AdminGoodDto?>

    fun getGoodById(
        id: Long?
    ): AdminGoodDto

    fun addGood(
        name: String?
    ): AdminGoodDto

    fun addGood(
        dto: AdminGoodDto?
    ): AdminGoodDto

    fun updateGood(
        dto: AdminGoodDto?
    ): AdminGoodDto

    fun deleteGood(
        article: String?
    ): Int
}
