package ru.chsergeig.shoppy.impl;

import org.jooq.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.chsergeig.shoppy.dao.AccountRepository;
import ru.chsergeig.shoppy.jooq.tables.daos.AccountsDao;

@Repository
public class AccountRepositoryImpl
        extends AccountsDao
        implements AccountRepository {

    public AccountRepositoryImpl(@Autowired Configuration configuration) {
        super(configuration);
    }


}
