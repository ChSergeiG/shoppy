package ru.chsergeig.shoppy.impl.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.chsergeig.shoppy.dao.OrderRepository;
import ru.chsergeig.shoppy.dto.admin.OrderDto;
import ru.chsergeig.shoppy.jooq.enums.Status;
import ru.chsergeig.shoppy.jooq.tables.pojos.Orders;
import ru.chsergeig.shoppy.mapping.OrderMapper;
import ru.chsergeig.shoppy.service.admin.AdminOrderService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminOrderServiceImpl implements AdminOrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;


    @Override
    public List<OrderDto> getAllOrders() {
        List<Orders> orders = orderRepository.fetchByStatus(Status.ADDED, Status.ACTIVE, Status.DISABLED);
        return orders.stream()
                .map(orderMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto getOrderById(Long id) {
        List<Orders> orders = orderRepository.fetchById(id.intValue());
        if (orders.isEmpty()) {
            return null;
        } else {
            return orderMapper.map(orders.get(0));
        }
    }

    @Override
    public OrderDto addOrder(String info) {
        Orders pojo = new Orders(null, info, Status.ADDED, null);
        orderRepository.insert(pojo);
        return orderMapper.map(pojo);
    }

    @Override
    public OrderDto addOrder(OrderDto dto) {
        Orders pojo = orderMapper.map(dto);
        orderRepository.insert(pojo);
        return orderMapper.map(pojo);
    }

    @Override
    public OrderDto updateOrder(OrderDto dto) {
        Orders pojo = orderMapper.map(dto);
        orderRepository.update(pojo);
        return orderMapper.map(pojo);
    }

    @Override
    public Integer deleteOrder(Integer id) {
        List<Orders> orders = orderRepository.fetchById(id);
        orders.forEach(o -> o.setStatus(Status.REMOVED));
        orderRepository.update(orders);
        return orders.size();
    }

}
