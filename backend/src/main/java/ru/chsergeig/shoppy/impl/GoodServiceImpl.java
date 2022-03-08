package ru.chsergeig.shoppy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chsergeig.shoppy.dao.GoodRepository;
import ru.chsergeig.shoppy.dto.GoodDTO;
import ru.chsergeig.shoppy.jooq.enums.Status;
import ru.chsergeig.shoppy.jooq.tables.pojos.Good;
import ru.chsergeig.shoppy.mapping.GoodMapper;
import ru.chsergeig.shoppy.service.GoodService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GoodServiceImpl implements GoodService {

    private final GoodRepository goodRepository;
    private final GoodMapper goodMapper;

    @Override
    public List<GoodDTO> getAllGoods() {
        List<Good> goods = goodRepository.fetchByStatus(Status.ADDED, Status.ACTIVE, Status.DISABLED);
        return goods.stream()
                .map(goodMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public GoodDTO addGood(String name) {
        Good pojo = new Good(null, name, null, Status.ADDED);
        goodRepository.insert(pojo);
        return goodMapper.map(pojo);
    }

    @Override
    public GoodDTO addGood(GoodDTO dto) {
        Good pojo = goodMapper.map(dto);
        goodRepository.insert(pojo);
        return goodMapper.map(pojo);
    }

    @Override
    public GoodDTO updateGood(GoodDTO dto) {
        Good pojo = goodMapper.map(dto);
        goodRepository.update(pojo);
        return goodMapper.map(pojo);
    }

    @Override
    @Transactional
    public Integer deleteGood(Integer article) {
        List<Good> goods = goodRepository.fetchByArticle(article);
        goods.forEach(g -> g.setStatus(Status.REMOVED));
        goodRepository.update(goods);
        return goods.size();
    }

}
