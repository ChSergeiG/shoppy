package ru.chsergeig.shoppy.impl.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chsergeig.shoppy.dao.GoodRepository;
import ru.chsergeig.shoppy.dto.admin.GoodDto;
import ru.chsergeig.shoppy.jooq.enums.Status;
import ru.chsergeig.shoppy.jooq.tables.pojos.Goods;
import ru.chsergeig.shoppy.mapping.GoodMapper;
import ru.chsergeig.shoppy.service.admin.AdminGoodService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static ru.chsergeig.shoppy.jooq.Tables.GOODS;

@Service
@RequiredArgsConstructor
public class AdminGoodServiceImpl
        implements AdminGoodService {

    private final GoodRepository goodRepository;
    private final GoodMapper goodMapper;

    @Override
    public List<GoodDto> getAllGoods() {
        List<Goods> goods = goodRepository.fetch(
                GOODS.STATUS,
                Status.ADDED, Status.ACTIVE, Status.DISABLED
        );
        return goods.stream()
                .map(goodMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public GoodDto getGoodById(Long id) {
        List<Goods> goods = goodRepository.fetch(
                GOODS.ID,
                id.intValue()
        );
        if (goods.isEmpty()) {
            return null;
        } else {
            return goodMapper.map(goods.get(0));
        }
    }

    @Override
    public GoodDto addGood(String name) {
        Goods pojo = new Goods(null, name, null, new BigDecimal("0.0"), Status.ADDED);
        goodRepository.insert(pojo);
        return goodMapper.map(pojo);
    }

    @Override
    public GoodDto addGood(GoodDto dto) {
        Goods pojo = goodMapper.map(dto);
        goodRepository.insert(pojo);
        return goodMapper.map(pojo);
    }

    @Override
    public GoodDto updateGood(GoodDto dto) {
        Goods pojo = goodMapper.map(dto);
        goodRepository.update(pojo);
        return goodMapper.map(pojo);
    }

    @Override
    @Transactional
    public int deleteGood(String article) {
        List<Goods> goods = goodRepository.fetch(
                GOODS.ARTICLE,
                article
        );
        goods.forEach(g -> g.setStatus(Status.REMOVED));
        goodRepository.update(goods);
        return goods.size();
    }

}
