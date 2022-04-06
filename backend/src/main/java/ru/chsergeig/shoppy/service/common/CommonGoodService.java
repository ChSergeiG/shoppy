package ru.chsergeig.shoppy.service.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.chsergeig.shoppy.dto.CommonGoodDto;

public interface CommonGoodService {

    /**
     * Get goods, which accords to given filter and using pagination
     *
     * @param filter
     * @param pageable
     */
    Page<CommonGoodDto> getAllGoodsUsingFilterAndPagination(String filter, Pageable pageable);

}
