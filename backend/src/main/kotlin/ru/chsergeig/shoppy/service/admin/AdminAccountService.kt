package ru.chsergeig.shoppy.service.admin

import ru.chsergeig.shoppy.dto.admin.AccountDto

interface AdminAccountService {

    fun getAllAccounts(): List<AccountDto?>

    fun getAccountByLogin(
        login: String?
    ): AccountDto

    fun addAccount(
        name: String?
    ): AccountDto

    fun addAccount(
        dto: AccountDto?
    ): AccountDto

    fun updateAccount(
        dto: AccountDto?
    ): AccountDto

    fun deleteAccount(
        name: String?
    ): Int
}
