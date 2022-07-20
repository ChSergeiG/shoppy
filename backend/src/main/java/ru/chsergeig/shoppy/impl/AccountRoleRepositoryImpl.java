package ru.chsergeig.shoppy.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.chsergeig.shoppy.dao.AccountRoleRepository;
import ru.chsergeig.shoppy.jooq.enums.AccountRole;
import ru.chsergeig.shoppy.jooq.tables.daos.AccountsRolesDao;
import ru.chsergeig.shoppy.jooq.tables.pojos.AccountsRoles;

import java.util.List;

import static ru.chsergeig.shoppy.jooq.Tables.ACCOUNTS;
import static ru.chsergeig.shoppy.jooq.Tables.ACCOUNTS_ROLES;

@Repository
public class AccountRoleRepositoryImpl
        extends AccountsRolesDao
        implements AccountRoleRepository {

    public AccountRoleRepositoryImpl(
            @Autowired Configuration configuration
    ) {
        super(configuration);
    }

    /**
     * Get account roles by its login
     */
    @Override
    @NotNull
    public List<AccountRole> fetchRolesByLogin(@Nullable String login) {
        return ctx().select(ACCOUNTS_ROLES.ROLE)
                .from(ACCOUNTS)
                .leftJoin(ACCOUNTS_ROLES).on(ACCOUNTS.ID.eq(ACCOUNTS_ROLES.ACCOUNT_ID))
                .where(ACCOUNTS.LOGIN.eq(login))
                .fetchInto(AccountRole.class);
    }

    @NotNull
    @Override
    public List<AccountsRoles> fetchByAccountId(@NotNull List<Integer> ids) {
        return ctx().selectFrom(ACCOUNTS_ROLES)
                .where(ACCOUNTS_ROLES.ACCOUNT_ID.in(ids))
                .fetchInto(AccountsRoles.class);
    }

    @Override
    public void deleteByAccountId(Integer id) {
        if (id == null) {
            return;
        }
        ctx().deleteFrom(ACCOUNTS_ROLES)
                .where(ACCOUNTS_ROLES.ACCOUNT_ID.eq(id))
                .execute();
    }


}
