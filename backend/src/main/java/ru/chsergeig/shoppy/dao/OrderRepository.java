package ru.chsergeig.shoppy.dao;

import org.jooq.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.chsergeig.shoppy.jooq.tables.daos.OrdersDao;

@Repository
public class OrderRepository
        extends OrdersDao {

    public OrderRepository(@Autowired Configuration configuration) {
        super(configuration);
    }

}
