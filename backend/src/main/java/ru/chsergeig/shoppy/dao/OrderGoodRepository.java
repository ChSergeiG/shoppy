package ru.chsergeig.shoppy.dao;

import org.jooq.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.chsergeig.shoppy.jooq.tables.daos.OrdersGoodsDao;

@Repository
public class OrderGoodRepository
        extends OrdersGoodsDao {

    public OrderGoodRepository(@Autowired Configuration configuration) {
        super(configuration);
    }

}
