package ru.chsergeig.shoppy.service.admin;

import ru.chsergeig.shoppy.dto.admin.GoodDto;

import java.util.List;

public interface GoodService {

    List<GoodDto> getAllGoods();

    GoodDto getGoodById(Long id);

    GoodDto addGood(String name);

    GoodDto addGood(GoodDto dto);

    GoodDto updateGood(GoodDto dto);

    Integer deleteGood(Integer article);

}
