/*
 * This file is generated by jOOQ.
 */
package ru.chsergeig.shoppy.jooq.tables.pojos;


import java.io.Serializable;

import ru.chsergeig.shoppy.jooq.enums.AccountRole;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AccountsRoles implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer     accountId;
    private AccountRole role;

    public AccountsRoles() {}

    public AccountsRoles(AccountsRoles value) {
        this.accountId = value.accountId;
        this.role = value.role;
    }

    public AccountsRoles(
        Integer     accountId,
        AccountRole role
    ) {
        this.accountId = accountId;
        this.role = role;
    }

    /**
     * Getter for <code>public.accounts_roles.account_id</code>.
     */
    public Integer getAccountId() {
        return this.accountId;
    }

    /**
     * Setter for <code>public.accounts_roles.account_id</code>.
     */
    public AccountsRoles setAccountId(Integer accountId) {
        this.accountId = accountId;
        return this;
    }

    /**
     * Getter for <code>public.accounts_roles.role</code>.
     */
    public AccountRole getRole() {
        return this.role;
    }

    /**
     * Setter for <code>public.accounts_roles.role</code>.
     */
    public AccountsRoles setRole(AccountRole role) {
        this.role = role;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("AccountsRoles (");

        sb.append(accountId);
        sb.append(", ").append(role);

        sb.append(")");
        return sb.toString();
    }
}
