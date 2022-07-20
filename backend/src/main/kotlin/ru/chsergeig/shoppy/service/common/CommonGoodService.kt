package ru.chsergeig.shoppy.service.common

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import ru.chsergeig.shoppy.dto.CommonGoodDto
import ru.chsergeig.shoppy.dto.admin.GoodDto

interface CommonGoodService {

    /**
     * Get goods, which accords to given filter and using pagination
     *
     * @param filter
     * @param pageable
     */
    fun getAllGoodsUsingFilterAndPagination(
        filter: String?,
        pageable: Pageable?
    ): Page<CommonGoodDto>

    fun getGoodsByIds(
        ids: Set<Int?>
    ): List<GoodDto>
}
