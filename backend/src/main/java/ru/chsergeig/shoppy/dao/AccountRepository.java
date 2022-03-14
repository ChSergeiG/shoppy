package ru.chsergeig.shoppy.dao;

import org.jooq.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.chsergeig.shoppy.jooq.enums.AccountRole;
import ru.chsergeig.shoppy.jooq.tables.daos.AccountsDao;

import java.util.List;

import static ru.chsergeig.shoppy.jooq.Tables.ACCOUNTS;
import static ru.chsergeig.shoppy.jooq.Tables.ACCOUNTS_ROLES;

@Repository
public class AccountRepository
        extends AccountsDao {

    public AccountRepository(@Autowired Configuration configuration) {
        super(configuration);
    }

    public List<AccountRole> fetchRolesByLogin(String login) {
        return ctx().select(ACCOUNTS_ROLES.ROLE)
                .from(ACCOUNTS)
                .leftJoin(ACCOUNTS_ROLES).on(ACCOUNTS.ID.eq(ACCOUNTS_ROLES.ACCOUNT_ID))
                .where(ACCOUNTS.LOGIN.eq(login))
                .fetchInto(AccountRole.class);
    }

}
