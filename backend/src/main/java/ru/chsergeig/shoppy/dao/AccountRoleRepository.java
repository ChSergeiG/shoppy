package ru.chsergeig.shoppy.dao;

import org.jooq.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.chsergeig.shoppy.jooq.enums.AccountRole;
import ru.chsergeig.shoppy.jooq.tables.daos.AccountsRolesDao;
import ru.chsergeig.shoppy.jooq.tables.pojos.AccountsRoles;
import ru.chsergeig.shoppy.jooq.tables.records.AccountsRolesRecord;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static ru.chsergeig.shoppy.jooq.Tables.ACCOUNTS;
import static ru.chsergeig.shoppy.jooq.Tables.ACCOUNTS_ROLES;

@Repository
public class AccountRoleRepository
        extends AccountsRolesDao {

    public AccountRoleRepository(
            @Autowired Configuration configuration
    ) {
        super(configuration);
    }

    /**
     * Get account roles by its login
     */
    public List<AccountRole> fetchRolesByLogin(String login) {
        return ctx().select(ACCOUNTS_ROLES.ROLE)
                .from(ACCOUNTS)
                .leftJoin(ACCOUNTS_ROLES).on(ACCOUNTS.ID.eq(ACCOUNTS_ROLES.ACCOUNT_ID))
                .where(ACCOUNTS.LOGIN.eq(login))
                .fetchInto(AccountRole.class);
    }

    public void deleteByAccountId(Integer id) {
        if (id == null) {
            return;
        }
        ctx().deleteFrom(ACCOUNTS_ROLES)
                .where(ACCOUNTS_ROLES.ACCOUNT_ID.eq(id))
                .execute();
    }
}