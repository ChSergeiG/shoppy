package ru.chsergeig.shoppy.impl.common;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.chsergeig.shoppy.dao.GoodRepository;
import ru.chsergeig.shoppy.dto.CommonGoodDto;
import ru.chsergeig.shoppy.dto.admin.GoodDto;
import ru.chsergeig.shoppy.jooq.tables.pojos.Goods;
import ru.chsergeig.shoppy.mapping.GoodMapper;
import ru.chsergeig.shoppy.service.common.CommonGoodService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommonGoodServiceImpl implements CommonGoodService {

    private final GoodMapper goodMapper;

    private final GoodRepository goodRepository;

    @Override
    public Page<CommonGoodDto> getAllGoodsUsingFilterAndPagination(String filter, Pageable pageable) {
        final List<Goods> goods = goodRepository.fetchByFilterAndPagination(filter, pageable);
        final Integer total = goodRepository.countActive();
        final List<CommonGoodDto> dtos = goods.stream()
                .map(pojo -> new CommonGoodDto(goodMapper.map(pojo)))
                .collect(Collectors.toList());
        return new PageImpl<>(dtos, pageable, total);
    }

    @Override
    public List<GoodDto> getGoodsByIds(Set<Integer> ids) {
        final List<Goods> goods = goodRepository.getGoodsByIds(ids);
        return goodMapper.mapList(goods);
    }

}
