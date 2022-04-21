package ru.chsergeig.shoppy.service.common;

import ru.chsergeig.shoppy.dto.ExtendedOrderDto;
import ru.chsergeig.shoppy.dto.admin.GoodDto;
import ru.chsergeig.shoppy.dto.admin.OrderDto;

import java.util.List;

public interface CommonOrdersService {

    String createOrder(List<GoodDto> goods, String username);

    ExtendedOrderDto getOrderByGuid(String guid, String username);

}

