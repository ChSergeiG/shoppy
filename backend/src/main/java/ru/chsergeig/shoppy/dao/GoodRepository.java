package ru.chsergeig.shoppy.dao;

import org.jooq.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.chsergeig.shoppy.jooq.tables.daos.GoodDao;

@Repository
public class GoodRepository
        extends GoodDao {

    public GoodRepository(@Autowired Configuration configuration) {
        super(configuration);
    }

}
