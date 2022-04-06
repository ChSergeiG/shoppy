package ru.chsergeig.shoppy.dao;

import org.jooq.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.chsergeig.shoppy.jooq.tables.daos.AccountsOrdersDao;

@Repository
public class AccountOrderRepository extends AccountsOrdersDao {

    public AccountOrderRepository(@Autowired Configuration configuration) {
        super(configuration);
    }

}
