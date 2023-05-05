package ru.chsergeig.shoppy.impl.common;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.chsergeig.shoppy.dao.GoodRepository;
import ru.chsergeig.shoppy.jooq.tables.pojos.Goods;
import ru.chsergeig.shoppy.mapping.GoodMapper;
import ru.chsergeig.shoppy.model.CommonGoodDto;
import ru.chsergeig.shoppy.service.common.CommonGoodService;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static ru.chsergeig.shoppy.jooq.Tables.GOODS;

@Service
@RequiredArgsConstructor
public class CommonGoodServiceImpl
        implements CommonGoodService {

    private final GoodMapper goodMapper;

    private final GoodRepository goodRepository;

    @Override
    @NotNull
    public Page<CommonGoodDto> getAllGoodsUsingFilterAndPagination(
            @Nullable String filter,
            @NotNull Pageable pageable
    ) {
        final List<Goods> goods = goodRepository.fetchByFilterAndPagination(filter, pageable);
        final Integer total = goodRepository.countActive();
        final List<CommonGoodDto> dtos = goods.stream()
                .map(goodMapper::mapCommon)
                .collect(Collectors.toList());
        return new PageImpl<>(dtos, pageable, total.longValue());
    }

    @Override
    @Nullable
    public List<CommonGoodDto> getGoodsByIds(
            @NotNull Collection<Integer> ids
    ) {
        final List<Goods> goods = goodRepository.fetch(
                GOODS.ID,
                ids.toArray(Integer[]::new)
        );
        return goodMapper.mapList(goods);
    }

}
