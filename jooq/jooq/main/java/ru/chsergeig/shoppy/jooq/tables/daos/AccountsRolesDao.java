/*
 * This file is generated by jOOQ.
 */
package ru.chsergeig.shoppy.jooq.tables.daos;


import org.jooq.Configuration;
import org.jooq.Record2;
import org.jooq.impl.DAOImpl;
import ru.chsergeig.shoppy.jooq.enums.AccountRole;
import ru.chsergeig.shoppy.jooq.tables.AccountsRoles;
import ru.chsergeig.shoppy.jooq.tables.records.AccountsRolesRecord;

import java.util.List;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AccountsRolesDao extends DAOImpl<AccountsRolesRecord, ru.chsergeig.shoppy.jooq.tables.pojos.AccountsRoles, Record2<Integer, AccountRole>> {

    /**
     * Create a new AccountsRolesDao without any configuration
     */
    public AccountsRolesDao() {
        super(AccountsRoles.ACCOUNTS_ROLES, ru.chsergeig.shoppy.jooq.tables.pojos.AccountsRoles.class);
    }

    /**
     * Create a new AccountsRolesDao with an attached configuration
     */
    public AccountsRolesDao(Configuration configuration) {
        super(AccountsRoles.ACCOUNTS_ROLES, ru.chsergeig.shoppy.jooq.tables.pojos.AccountsRoles.class, configuration);
    }

    @Override
    public Record2<Integer, AccountRole> getId(ru.chsergeig.shoppy.jooq.tables.pojos.AccountsRoles object) {
        return compositeKeyRecord(object.getAccountId(), object.getRole());
    }

    /**
     * Fetch records that have <code>account_id BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<ru.chsergeig.shoppy.jooq.tables.pojos.AccountsRoles> fetchRangeOfAccountId(Integer lowerInclusive, Integer upperInclusive) {
        return fetchRange(AccountsRoles.ACCOUNTS_ROLES.ACCOUNT_ID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>account_id IN (values)</code>
     */
    public List<ru.chsergeig.shoppy.jooq.tables.pojos.AccountsRoles> fetchByAccountId(Integer... values) {
        return fetch(AccountsRoles.ACCOUNTS_ROLES.ACCOUNT_ID, values);
    }

    /**
     * Fetch records that have <code>role BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<ru.chsergeig.shoppy.jooq.tables.pojos.AccountsRoles> fetchRangeOfRole(AccountRole lowerInclusive, AccountRole upperInclusive) {
        return fetchRange(AccountsRoles.ACCOUNTS_ROLES.ROLE, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>role IN (values)</code>
     */
    public List<ru.chsergeig.shoppy.jooq.tables.pojos.AccountsRoles> fetchByRole(AccountRole... values) {
        return fetch(AccountsRoles.ACCOUNTS_ROLES.ROLE, values);
    }
}
