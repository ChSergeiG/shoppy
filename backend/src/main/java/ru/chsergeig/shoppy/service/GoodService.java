package ru.chsergeig.shoppy.service;

import ru.chsergeig.shoppy.dto.GoodDTO;

import java.util.List;

public interface GoodService {

    List<GoodDTO> getAllGoods();

    GoodDTO addGood(String name);

    GoodDTO addGood(GoodDTO dto);

    GoodDTO updateGood(GoodDTO dto);

    Integer deleteGood(Integer article);

}
