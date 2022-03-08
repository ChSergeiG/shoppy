package ru.chsergeig.shoppy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.chsergeig.shoppy.dao.OrderRepository;
import ru.chsergeig.shoppy.dto.OrderDTO;
import ru.chsergeig.shoppy.jooq.enums.Status;
import ru.chsergeig.shoppy.jooq.tables.pojos.Good;
import ru.chsergeig.shoppy.jooq.tables.pojos.Order;
import ru.chsergeig.shoppy.mapping.OrderMapper;
import ru.chsergeig.shoppy.service.OrderService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;


    @Override
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.fetchByStatus(Status.ADDED, Status.ACTIVE, Status.DISABLED);
        return orders.stream()
                .map(orderMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO addOrder(String info) {
        Order pojo = new Order(null,   info, Status.ADDED);
        orderRepository.insert(pojo);
        return orderMapper.map(pojo);
    }

    @Override
    public OrderDTO addOrder(OrderDTO dto) {
        Order pojo = orderMapper.map(dto);
        orderRepository.insert(pojo);
        return orderMapper.map(pojo);
    }

    @Override
    public OrderDTO updateOrder(OrderDTO dto) {
        Order pojo = orderMapper.map(dto);
        orderRepository.update(pojo);
        return orderMapper.map(pojo);
    }

    @Override
    public Integer deleteOrder(Integer id) {
        List<Order> orders = orderRepository.fetchById(id);
        orders.forEach(o -> o.setStatus(Status.REMOVED));
        orderRepository.update(orders);
        return orders.size();
    }

}
