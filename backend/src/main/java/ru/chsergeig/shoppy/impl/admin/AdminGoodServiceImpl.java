package ru.chsergeig.shoppy.impl.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chsergeig.shoppy.dao.GoodRepository;
import ru.chsergeig.shoppy.jooq.enums.Status;
import ru.chsergeig.shoppy.jooq.tables.pojos.Goods;
import ru.chsergeig.shoppy.mapping.GoodMapper;
import ru.chsergeig.shoppy.model.AdminGoodDto;
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
    public List<AdminGoodDto> getAllGoods() {
        List<Goods> goods = goodRepository.fetch(
                GOODS.STATUS,
                Status.ADDED, Status.ACTIVE, Status.DISABLED
        );
        return goods.stream()
                .map(goodMapper::mapAdmin)
                .collect(Collectors.toList());
    }

    @Override
    public AdminGoodDto getGoodById(Long id) {
        List<Goods> goods = goodRepository.fetch(
                GOODS.ID,
                id.intValue()
        );
        if (goods.isEmpty()) {
            return null;
        } else {
            return goodMapper.mapAdmin(goods.get(0));
        }
    }

    @Override
    public AdminGoodDto addGood(String name) {
        Goods pojo = new Goods(null, name, null, new BigDecimal("0.0"), Status.ADDED);
        goodRepository.insert(pojo);
        return goodMapper.mapAdmin(pojo);
    }

    @Override
    public AdminGoodDto addGood(AdminGoodDto dto) {
        Goods pojo = goodMapper.mapAdmin(dto);
        goodRepository.insert(pojo);
        return goodMapper.mapAdmin(pojo);
    }

    @Override
    public AdminGoodDto updateGood(AdminGoodDto dto) {
        Goods pojo = goodMapper.mapAdmin(dto);
        goodRepository.update(pojo);
        return goodMapper.mapAdmin(pojo);
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
