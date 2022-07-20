package ru.chsergeig.shoppy.impl;

import org.jooq.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.chsergeig.shoppy.dao.OrderRepository;
import ru.chsergeig.shoppy.jooq.tables.daos.OrdersDao;

@Repository
public class OrderRepositoryImpl
        extends OrdersDao
        implements OrderRepository {

    public OrderRepositoryImpl(@Autowired Configuration configuration) {
        super(configuration);
    }

}
