package ru.chsergeig.shoppy.service.admin;

import ru.chsergeig.shoppy.dto.admin.AccountDto;
import ru.chsergeig.shoppy.dto.admin.CountedGoodDto;
import ru.chsergeig.shoppy.dto.admin.GoodDto;
import ru.chsergeig.shoppy.dto.admin.OrderDto;

import java.util.List;

public interface AdminOrderService {

    List<OrderDto> getAllOrders();

    OrderDto getOrderById(Long id);

    OrderDto addOrder(String info);

    OrderDto addOrder(OrderDto dto);

    OrderDto updateOrder(OrderDto dto);

    Integer deleteOrder(Integer id);

    List<AccountDto> getAccountsByOrderId(Integer id);

    List<CountedGoodDto> getGoodsByOrderId(Integer id);

}
