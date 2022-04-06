package ru.chsergeig.shoppy.service.common;

import ru.chsergeig.shoppy.dto.admin.GoodDto;

import java.util.List;

public interface CommonOrdersService {

    String createOrder(List<GoodDto> goods, String username);

}
