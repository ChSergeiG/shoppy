package ru.chsergeig.shoppy.impl.admin;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jooq.Record2;
import org.jooq.Record7;
import org.jooq.Result;
import org.springframework.stereotype.Service;
import ru.chsergeig.shoppy.dao.AccountOrderRepository;
import ru.chsergeig.shoppy.dao.OrderGoodRepository;
import ru.chsergeig.shoppy.dao.OrderRepository;
import ru.chsergeig.shoppy.exception.ServiceException;
import ru.chsergeig.shoppy.jooq.enums.Status;
import ru.chsergeig.shoppy.jooq.tables.pojos.Orders;
import ru.chsergeig.shoppy.mapping.AccountMapper;
import ru.chsergeig.shoppy.mapping.EnumMapper;
import ru.chsergeig.shoppy.mapping.GoodMapper;
import ru.chsergeig.shoppy.mapping.OrderMapper;
import ru.chsergeig.shoppy.model.AdminAccountDto;
import ru.chsergeig.shoppy.model.AdminCountedGoodDto;
import ru.chsergeig.shoppy.model.AdminOrderAccountsDto;
import ru.chsergeig.shoppy.model.AdminOrderDto;
import ru.chsergeig.shoppy.model.AdminOrderGoodsDto;
import ru.chsergeig.shoppy.service.admin.AdminOrderService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static ru.chsergeig.shoppy.jooq.Tables.ORDERS;

@Service
@RequiredArgsConstructor
public class AdminOrderServiceImpl implements AdminOrderService {

    private final AccountMapper accountMapper;
    private final AccountOrderRepository accountOrderRepository;
    private final EnumMapper enumMapper;
    private final GoodMapper goodMapper;
    private final OrderGoodRepository orderGoodRepository;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    @NotNull
    @Override
    public List<AdminOrderDto> getAllOrders() {
        List<Orders> orders = orderRepository.fetch(
                ORDERS.STATUS,
                Status.ADDED, Status.ACTIVE, Status.DISABLED
        );
        return orders.stream()
                .map(orderMapper::mapAdmin)
                .collect(Collectors.toList());
    }

    @NotNull
    @Override
    public AdminOrderDto getOrderById(Long id) {
        if (id == null) {
            throw new ServiceException("Order id is null");
        }
        List<Orders> orders = orderRepository.fetch(
                ORDERS.ID,
                id.intValue()
        );
        if (orders.isEmpty()) {
            throw new ServiceException("Orders is empty");
        } else {
            return orderMapper.mapAdmin(orders.get(0));
        }
    }

    @NotNull
    @Override
    public AdminOrderDto addOrder(String info) {
        Orders pojo = new Orders(null, info, Status.ADDED, null);
        orderRepository.insert(pojo);
        return orderMapper.mapAdmin(pojo);
    }

    @NotNull
    @Override
    public AdminOrderDto addOrder(AdminOrderDto dto) {
        Orders pojo = orderMapper.mapAdmin(dto);
        orderRepository.insert(pojo);
        return orderMapper.mapAdmin(pojo);
    }

    @NotNull
    @Override
    public AdminOrderDto updateOrder(AdminOrderDto dto) {
        Orders pojo = orderMapper.mapAdmin(dto);
        orderRepository.update(pojo);
        return orderMapper.mapAdmin(pojo);
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

    @NotNull
    @Override
    public List<AdminAccountDto> getAccountsByOrderId(Integer id) {
        return accountMapper.mapAdminList(
                accountOrderRepository.getAccountsByOrderId(id)
        );
    }

    @NotNull
    @Override
    public List<AdminOrderAccountsDto> getAccountsByOrderIds(
            @NotNull List<Integer> ids
    ) {
        Result<Record2<String, Integer>> records = orderGoodRepository.getAccountsByOrderIds(ids);
        return ids.stream()
                .map((id) -> {
                    final AdminOrderAccountsDto result = new AdminOrderAccountsDto();
                    result.setOrderId(id);
                    result.setAccounts(
                            records.stream()
                                    .filter((gr) -> gr.component2() == id)
                                    .map(Record2::component1)
                                    .distinct()
                                    .collect(Collectors.toList())
                    );
                    return result;
                })
                .collect(Collectors.toList());
    }

    @NotNull
    @Override
    public List<AdminCountedGoodDto> getGoodsByOrderId(Integer id) {
        return orderGoodRepository.getGoodsByOrderId(id).entrySet().stream()
                .map(e -> goodMapper.mapAdminCounted(e.getKey(), e.getValue().intValue()))
                .collect(Collectors.toList());
    }

    @NotNull
    @Override
    public List<AdminOrderGoodsDto> getGoodsByOrderIds(
            @NotNull List<Integer> ids
    ) {
        Result<Record7<Integer, Long, Integer, String, String, BigDecimal, Status>> records = orderGoodRepository.getGoodsByOrderIds(ids);
        return ids.stream()
                .map((id) -> {
                    final AdminOrderGoodsDto result = new AdminOrderGoodsDto();
                    result.setOrderId(id);
                    result.setGoods(
                            records.stream()
                                    .filter((gr) -> gr.component1() == id)
                                    .map((gr) -> {
                                        final AdminCountedGoodDto dto = new AdminCountedGoodDto();
                                        dto.setCount(gr.component2() != null ? gr.component2().intValue() : 0);
                                        dto.setId(gr.component3());
                                        dto.setName(gr.component4());
                                        dto.setArticle(gr.component5());
                                        dto.setPrice(gr.component6());
                                        dto.setStatus(enumMapper.fromJooq(gr.component7()));
                                        return dto;
                                    })
                                    .collect(Collectors.toList())
                    );
                    return result;
                })
                .collect(Collectors.toList());

    }
}
