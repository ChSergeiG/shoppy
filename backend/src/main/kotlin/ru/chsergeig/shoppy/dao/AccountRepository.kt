package ru.chsergeig.shoppy.dao

import org.jooq.DAO
import ru.chsergeig.shoppy.jooq.tables.pojos.Accounts
import ru.chsergeig.shoppy.jooq.tables.records.AccountsRecord

interface AccountRepository : DAO<AccountsRecord, Accounts, Int>
