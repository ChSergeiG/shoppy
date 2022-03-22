package ru.chsergeig.shoppy.dao;

import org.jooq.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.chsergeig.shoppy.jooq.tables.daos.AccountsDao;

@Repository
public class AccountRepository
        extends AccountsDao {

    public AccountRepository(@Autowired Configuration configuration) {
        super(configuration);
    }


}
