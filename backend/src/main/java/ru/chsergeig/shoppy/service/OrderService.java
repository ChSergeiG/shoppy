package ru.chsergeig.shoppy.service;

import ru.chsergeig.shoppy.dto.OrderDTO;

import java.util.List;

public interface OrderService {

    List<OrderDTO> getAllOrders();

    OrderDTO addOrder(String info);

    OrderDTO addOrder(OrderDTO dto);

    OrderDTO updateOrder(OrderDTO dto);

    Integer deleteOrder(Integer id);

}
