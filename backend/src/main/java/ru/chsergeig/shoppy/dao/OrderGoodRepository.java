package ru.chsergeig.shoppy.dao;

import org.jooq.Configuration;
import org.jooq.Record6;
import org.jooq.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.chsergeig.shoppy.jooq.enums.Status;
import ru.chsergeig.shoppy.jooq.tables.daos.OrdersGoodsDao;
import ru.chsergeig.shoppy.jooq.tables.pojos.Goods;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.chsergeig.shoppy.jooq.Tables.GOODS;
import static ru.chsergeig.shoppy.jooq.Tables.ORDERS_GOODS;

@Repository
public class OrderGoodRepository
        extends OrdersGoodsDao {

    public OrderGoodRepository(@Autowired Configuration configuration) {
        super(configuration);
    }

    public Map<Goods, Long> getGoodsByOrderId(Integer orderId) {
        Result<Record6<Integer, String, String, BigDecimal, Status, Long>> records = ctx().select(
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
}
