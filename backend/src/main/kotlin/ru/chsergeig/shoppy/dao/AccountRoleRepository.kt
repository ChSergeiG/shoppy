package ru.chsergeig.shoppy.dao

import org.jooq.DAO
import org.jooq.Record2
import ru.chsergeig.shoppy.jooq.enums.AccountRole
import ru.chsergeig.shoppy.jooq.tables.pojos.AccountsRoles
import ru.chsergeig.shoppy.jooq.tables.records.AccountsRolesRecord

interface AccountRoleRepository :
    DAO<AccountsRolesRecord, AccountsRoles, Record2<Int?, AccountRole?>> {

    fun fetchRolesByLogin(
        login: String?
    ): List<AccountRole>

    fun fetchByAccountId(
        ids: List<Int>
    ): MutableList<AccountsRoles>

    fun deleteByAccountId(
        id: Int?
    )
}
