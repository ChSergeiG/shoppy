package ru.chsergeig.shoppy.dao;

import org.jooq.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.chsergeig.shoppy.jooq.tables.daos.UserDao;

@Repository
public class UserRepository
        extends UserDao {

    public UserRepository(@Autowired Configuration configuration) {
        super(configuration);
    }

}
