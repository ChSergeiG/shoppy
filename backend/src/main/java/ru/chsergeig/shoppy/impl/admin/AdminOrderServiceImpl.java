package ru.chsergeig.shoppy.impl.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.chsergeig.shoppy.dao.AccountOrderRepository;
import ru.chsergeig.shoppy.dao.OrderGoodRepository;
import ru.chsergeig.shoppy.dao.OrderRepository;
import ru.chsergeig.shoppy.dto.admin.AccountDto;
import ru.chsergeig.shoppy.dto.admin.CountedGoodDto;
import ru.chsergeig.shoppy.dto.admin.OrderDto;
import ru.chsergeig.shoppy.jooq.enums.Status;
import ru.chsergeig.shoppy.jooq.tables.pojos.Orders;
import ru.chsergeig.shoppy.mapping.AccountMapper;
import ru.chsergeig.shoppy.mapping.GoodMapper;
import ru.chsergeig.shoppy.mapping.OrderMapper;
import ru.chsergeig.shoppy.service.admin.AdminOrderService;

import java.util.List;
import java.util.stream.Collectors;

import static ru.chsergeig.shoppy.jooq.Tables.ORDERS;

@Service
@RequiredArgsConstructor
public class AdminOrderServiceImpl implements AdminOrderService {

    private final AccountMapper accountMapper;
    private final AccountOrderRepository accountOrderRepository;
    private final GoodMapper goodMapper;
    private final OrderGoodRepository orderGoodRepository;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;


    @Override
    public List<OrderDto> getAllOrders() {
        List<Orders> orders = orderRepository.fetch(
                ORDERS.STATUS,
                Status.ADDED, Status.ACTIVE, Status.DISABLED
        );
        return orders.stream()
                .map(orderMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto getOrderById(Long id) {
        List<Orders> orders = orderRepository.fetch(
                ORDERS.ID,
                id.intValue()
        );
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
    public int deleteOrder(Integer id) {
        List<Orders> orders = orderRepository.fetch(
                ORDERS.ID,
                id
        );
        orders.forEach(o -> o.setStatus(Status.REMOVED));
        orderRepository.update(orders);
        return orders.size();
    }

    @Override
    public List<AccountDto> getAccountsByOrderId(Integer id) {
        return accountMapper.mapList(
                accountOrderRepository.getAccountsByOrderId(id)
        );
    }

    @Override
    public List<CountedGoodDto> getGoodsByOrderId(Integer id) {
        return orderGoodRepository.getGoodsByOrderId(id).entrySet().stream()
                .map(e -> new CountedGoodDto(goodMapper.map(e.getKey()), e.getValue().intValue()))
                .collect(Collectors.toList());
    }

}
