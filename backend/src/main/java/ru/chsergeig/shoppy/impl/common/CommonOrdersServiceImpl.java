package ru.chsergeig.shoppy.impl.common;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chsergeig.shoppy.dao.AccountOrderRepository;
import ru.chsergeig.shoppy.dao.AccountRepository;
import ru.chsergeig.shoppy.dao.GoodRepository;
import ru.chsergeig.shoppy.dao.OrderGoodRepository;
import ru.chsergeig.shoppy.dao.OrderRepository;
import ru.chsergeig.shoppy.exception.ControllerException;
import ru.chsergeig.shoppy.jooq.enums.Status;
import ru.chsergeig.shoppy.jooq.tables.pojos.Accounts;
import ru.chsergeig.shoppy.jooq.tables.pojos.AccountsOrders;
import ru.chsergeig.shoppy.jooq.tables.pojos.Goods;
import ru.chsergeig.shoppy.jooq.tables.pojos.Orders;
import ru.chsergeig.shoppy.jooq.tables.pojos.OrdersGoods;
import ru.chsergeig.shoppy.mapping.GoodMapper;
import ru.chsergeig.shoppy.mapping.OrderMapper;
import ru.chsergeig.shoppy.model.CommonGoodDto;
import ru.chsergeig.shoppy.model.ExtendedOrderDto;
import ru.chsergeig.shoppy.model.OrderEntry;
import ru.chsergeig.shoppy.service.common.CommonOrdersService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static ru.chsergeig.shoppy.jooq.Tables.ACCOUNTS;
import static ru.chsergeig.shoppy.jooq.Tables.GOODS;
import static ru.chsergeig.shoppy.jooq.Tables.ORDERS;

@Service
@RequiredArgsConstructor
public class CommonOrdersServiceImpl implements CommonOrdersService {

    private final AccountOrderRepository accountOrderRepository;
    private final AccountRepository accountRepository;
    private final GoodMapper goodMapper;
    private final GoodRepository goodRepository;
    private final OrderGoodRepository orderGoodRepository;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    @NotNull
    @Override
    @Transactional
    public String createOrder(
            @Nullable List<CommonGoodDto> goods,
            @Nullable String username
    ) {
        List<Accounts> accounts = accountRepository.fetch(
                ACCOUNTS.LOGIN,
                username
        );
        if (accounts.size() != 1 || accounts.get(0) == null) {
            throw new RuntimeException("Invalid username");
        }
        final String guid = UUID.randomUUID().toString();
        final Accounts account = accounts.get(0);
        final Orders newOrder = new Orders(null, "Created by web app in " + LocalDateTime.now(), Status.ADDED, guid);
        orderRepository.insert(newOrder);

        accountOrderRepository.insert(new AccountsOrders(account.getId(), newOrder.getId()));
        final Map<CommonGoodDto, Integer> mapOfGoods = (goods == null ? List.<CommonGoodDto>of() : goods).stream()
                .reduce(
                        new HashMap<>(),
                        (map, dto) -> {
                            if (map.containsKey(dto)) {
                                map.put(dto, map.get(dto) + 1);
                            } else {
                                map.put(dto, 1);
                            }
                            return map;
                        },
                        (map1, map2) -> {
                            for (Map.Entry<CommonGoodDto, Integer> entry : map1.entrySet()) {
                                if (map2.containsKey(entry.getKey())) {
                                    map2.put(entry.getKey(), entry.getValue() + map2.get(entry.getKey()));
                                } else {
                                    map2.put(entry.getKey(), entry.getValue());
                                }
                            }
                            return map2;
                        }
                );
        List<Goods> resolvedGoods = goodRepository.fetch(
                GOODS.ARTICLE,
                mapOfGoods.keySet().stream()
                        .map(CommonGoodDto::getArticle)
                        .toArray(String[]::new)
        );

        final List<OrdersGoods> ordersGoodsList = mapOfGoods.entrySet().stream()
                .map(e -> new OrdersGoods(
                        newOrder.getId(),
                        resolvedGoods.stream()
                                .filter(g -> g.getArticle().equals(e.getKey().getArticle()))
                                .findFirst()
                                .map(Goods::getId)
                                .orElseThrow(() -> new RuntimeException("Cant find good by given article: " + e.getKey().getArticle())),
                        e.getValue().longValue()
                ))
                .collect(Collectors.toList());
        orderGoodRepository.insert(ordersGoodsList);
        return guid;
    }

    @NotNull
    @Override
    public ExtendedOrderDto getOrderByGuid(
            @Nullable String guid,
            @Nullable String username
    ) {
        List<Orders> orders = orderRepository.fetch(
                ORDERS.GUID,
                guid
        );
        if (orders.size() != 1) {
            throw new ControllerException(HttpStatus.EXPECTATION_FAILED, "Wrong orders count obtained by guid", null);
        }
        Orders pojo = orders.get(0);
        List<Accounts> associatedAccounts = accountOrderRepository.getAccountsByOrderId(pojo.getId());
        if (associatedAccounts.stream().noneMatch(a -> a.getLogin().equals(username))) {
            throw new ControllerException(HttpStatus.UNAUTHORIZED, "Incorrect user", null);
        }
        ExtendedOrderDto result = orderMapper.mapExtended(pojo);
        result.setGuid(guid);
        result.setPropertyEntries(
                orderGoodRepository.getGoodsByOrderId(pojo.getId()).entrySet().stream()
                        .map(e -> new OrderEntry(goodMapper.mapCommon(e.getKey()), e.getValue().intValue()))
                        .collect(Collectors.toList())
        );
        return result;
    }
}
