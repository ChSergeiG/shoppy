package ru.chsergeig.shoppy.dao

import org.jooq.DAO
import org.springframework.data.domain.Pageable
import ru.chsergeig.shoppy.jooq.tables.pojos.Goods
import ru.chsergeig.shoppy.jooq.tables.records.GoodsRecord

interface GoodRepository : DAO<GoodsRecord?, Goods?, Int?> {

    fun fetchByFilterAndPagination(
        filter: String?,
        pageable: Pageable?
    ): List<Goods>

    fun countActive(): Int?
}
