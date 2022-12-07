package ru.chsergeig.shoppy.impl;

import org.jetbrains.annotations.NotNull;
import org.jooq.Configuration;
import org.jooq.Record2;
import org.jooq.Record6;
import org.jooq.Record7;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.chsergeig.shoppy.dao.OrderGoodRepository;
import ru.chsergeig.shoppy.jooq.enums.Status;
import ru.chsergeig.shoppy.jooq.tables.daos.OrdersGoodsDao;
import ru.chsergeig.shoppy.jooq.tables.pojos.Goods;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.chsergeig.shoppy.jooq.Tables.ACCOUNTS;
import static ru.chsergeig.shoppy.jooq.Tables.ACCOUNTS_ORDERS;
import static ru.chsergeig.shoppy.jooq.Tables.GOODS;
import static ru.chsergeig.shoppy.jooq.Tables.ORDERS;
import static ru.chsergeig.shoppy.jooq.Tables.ORDERS_GOODS;

@Repository
public class OrderGoodRepositoryImpl
        extends OrdersGoodsDao
        implements OrderGoodRepository {

    public OrderGoodRepositoryImpl(
            @Autowired Configuration configuration
    ) {
        super(configuration);
    }

    @NotNull
    public Map<Goods, Long> getGoodsByOrderId(
            Integer orderId
    ) {
        Result<Record6<Integer, String, String, BigDecimal, Status, Long>> records = ctx()
                .select(
                        GOODS.ID,
                        GOODS.NAME,
                        GOODS.ARTICLE,
                        GOODS.PRICE,
                        GOODS.STATUS,
                        ORDERS_GOODS.COUNT
                )
                .from(GOODS)
                .leftJoin(ORDERS_GOODS).on(ORDERS_GOODS.GOOD_ID.eq(GOODS.ID))
                .where(ORDERS_GOODS.ORDER_ID.eq(orderId))
                .fetch();
        return records.stream().collect(Collectors.toMap(
                r -> new Goods(
                        r.value1(),
                        r.value2(),
                        r.value3(),
                        r.value4(),
                        r.value5()
                ),
                Record6::value6
        ));
    }

    @NotNull
    @Override
    public Result<Record2<String, Integer>> getAccountsByOrderIds(
            @NotNull List<Integer> orderIds
    ) {
        return ctx()
                .select(
                        ACCOUNTS.LOGIN,
                        ORDERS.ID
                )
                .from(ACCOUNTS)
                .innerJoin(ACCOUNTS_ORDERS).on(ACCOUNTS.ID.eq(ACCOUNTS_ORDERS.ACCOUNT_ID))
                .innerJoin(ORDERS).on(ORDERS.ID.eq(ACCOUNTS_ORDERS.ORDER_ID))
                .where(ORDERS.ID.in(orderIds))
                .fetch();
    }

    @NotNull
    @Override
    public Result<Record7<Integer, Long, Integer, String, String, BigDecimal, Status>> getGoodsByOrderIds(@NotNull List<Integer> orderIds) {
        return ctx()
                .select(
                        ORDERS.ID,
                        ORDERS_GOODS.COUNT,
                        GOODS.ID,
                        GOODS.NAME,
                        GOODS.ARTICLE,
                        GOODS.PRICE,
                        GOODS.STATUS
                )
                .from(ORDERS)
                .innerJoin(ORDERS_GOODS).on(ORDERS.ID.eq(ORDERS_GOODS.ORDER_ID))
                .innerJoin(GOODS).on(GOODS.ID.eq(ORDERS_GOODS.GOOD_ID))
                .where(ORDERS.ID.in(orderIds))
                .fetch();
    }


}
