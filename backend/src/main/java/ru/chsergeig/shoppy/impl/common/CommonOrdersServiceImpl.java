package ru.chsergeig.shoppy.impl.common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chsergeig.shoppy.dao.AccountOrderRepository;
import ru.chsergeig.shoppy.dao.AccountRepository;
import ru.chsergeig.shoppy.dao.GoodRepository;
import ru.chsergeig.shoppy.dao.OrderGoodRepository;
import ru.chsergeig.shoppy.dao.OrderRepository;
import ru.chsergeig.shoppy.dto.admin.GoodDto;
import ru.chsergeig.shoppy.jooq.enums.Status;
import ru.chsergeig.shoppy.jooq.tables.pojos.Accounts;
import ru.chsergeig.shoppy.jooq.tables.pojos.AccountsOrders;
import ru.chsergeig.shoppy.jooq.tables.pojos.Goods;
import ru.chsergeig.shoppy.jooq.tables.pojos.Orders;
import ru.chsergeig.shoppy.jooq.tables.pojos.OrdersGoods;
import ru.chsergeig.shoppy.service.common.CommonOrdersService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommonOrdersServiceImpl implements CommonOrdersService {

    private final AccountOrderRepository accountOrderRepository;
    private final AccountRepository accountRepository;
    private final GoodRepository goodRepository;
    private final OrderGoodRepository orderGoodRepository;
    private final OrderRepository orderRepository;

    @Override
    @Transactional
    public String createOrder(List<GoodDto> goods, String username) {
        List<Accounts> accounts = accountRepository.fetchByLogin(username);
        if (accounts.size() != 1 || accounts.get(0) == null) {
            throw new RuntimeException("Invalid username");
        }
        final String guid = UUID.randomUUID().toString();
        final Accounts account = accounts.get(0);
        final Orders newOrder = new Orders(null, "Created by web app in " + LocalDateTime.now(), Status.ADDED, guid);
        orderRepository.insert(newOrder);

        accountOrderRepository.insert(new AccountsOrders(account.getId(), newOrder.getId()));
        final Map<GoodDto, Integer> mapOfGoods = goods.stream()
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
                            for (Map.Entry<GoodDto, Integer> entry : map1.entrySet()) {
                                if (map2.containsKey(entry.getKey())) {
                                    map2.put(entry.getKey(), entry.getValue() + map2.get(entry.getKey()));
                                } else {
                                    map2.put(entry.getKey(), entry.getValue());
                                }
                            }
                            return map2;
                        }
                );
        List<Goods> resolvedGoods = goodRepository.fetchByArticle(
                mapOfGoods.keySet().stream()
                        .map(GoodDto::getArticle)
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


}
