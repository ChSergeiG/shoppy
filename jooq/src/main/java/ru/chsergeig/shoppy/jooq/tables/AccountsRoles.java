/*
 * This file is generated by jOOQ.
 */
package ru.chsergeig.shoppy.jooq.tables;


import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row2;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import ru.chsergeig.shoppy.jooq.Public;
import ru.chsergeig.shoppy.jooq.enums.AccountRole;
import ru.chsergeig.shoppy.jooq.tables.records.AccountsRolesRecord;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AccountsRoles extends TableImpl<AccountsRolesRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.accounts_roles</code>
     */
    public static final AccountsRoles ACCOUNTS_ROLES = new AccountsRoles();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<AccountsRolesRecord> getRecordType() {
        return AccountsRolesRecord.class;
    }

    /**
     * The column <code>public.accounts_roles.account_id</code>.
     */
    public final TableField<AccountsRolesRecord, Integer> ACCOUNT_ID = createField(DSL.name("account_id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.accounts_roles.role</code>.
     */
    public final TableField<AccountsRolesRecord, AccountRole> ROLE = createField(DSL.name("role"), SQLDataType.VARCHAR.nullable(false).defaultValue(DSL.field("'ROLE_GUEST'::account_role", SQLDataType.VARCHAR)).asEnumDataType(ru.chsergeig.shoppy.jooq.enums.AccountRole.class), this, "");

    private AccountsRoles(Name alias, Table<AccountsRolesRecord> aliased) {
        this(alias, aliased, null);
    }

    private AccountsRoles(Name alias, Table<AccountsRolesRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.accounts_roles</code> table reference
     */
    public AccountsRoles(String alias) {
        this(DSL.name(alias), ACCOUNTS_ROLES);
    }

    /**
     * Create an aliased <code>public.accounts_roles</code> table reference
     */
    public AccountsRoles(Name alias) {
        this(alias, ACCOUNTS_ROLES);
    }

    /**
     * Create a <code>public.accounts_roles</code> table reference
     */
    public AccountsRoles() {
        this(DSL.name("accounts_roles"), null);
    }

    public <O extends Record> AccountsRoles(Table<O> child, ForeignKey<O, AccountsRolesRecord> key) {
        super(child, key, ACCOUNTS_ROLES);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public Identity<AccountsRolesRecord, Integer> getIdentity() {
        return (Identity<AccountsRolesRecord, Integer>) super.getIdentity();
    }

    @Override
    public AccountsRoles as(String alias) {
        return new AccountsRoles(DSL.name(alias), this);
    }

    @Override
    public AccountsRoles as(Name alias) {
        return new AccountsRoles(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public AccountsRoles rename(String name) {
        return new AccountsRoles(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public AccountsRoles rename(Name name) {
        return new AccountsRoles(name, null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row2<Integer, AccountRole> fieldsRow() {
        return (Row2) super.fieldsRow();
    }
}