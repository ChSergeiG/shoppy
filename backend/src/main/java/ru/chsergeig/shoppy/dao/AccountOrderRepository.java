package ru.chsergeig.shoppy.dao;

import org.jooq.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.chsergeig.shoppy.jooq.tables.daos.AccountsOrdersDao;
import ru.chsergeig.shoppy.jooq.tables.pojos.Accounts;

import java.util.List;

import static ru.chsergeig.shoppy.jooq.Tables.ACCOUNTS;
import static ru.chsergeig.shoppy.jooq.Tables.ACCOUNTS_ORDERS;

@Repository
public class AccountOrderRepository extends AccountsOrdersDao {

    public AccountOrderRepository(@Autowired Configuration configuration) {
        super(configuration);
    }

    public List<Accounts> getAccountsByOrderId(Integer orderId) {
        return ctx().select(ACCOUNTS.asterisk())
                .from(ACCOUNTS)
                .leftJoin(ACCOUNTS_ORDERS).on(ACCOUNTS_ORDERS.ACCOUNT_ID.eq(ACCOUNTS.ID))
                .where(ACCOUNTS_ORDERS.ORDER_ID.eq(orderId))
                .fetchInto(Accounts.class);
    }

}
