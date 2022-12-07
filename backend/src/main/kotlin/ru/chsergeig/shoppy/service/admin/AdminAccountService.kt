package ru.chsergeig.shoppy.service.admin

import ru.chsergeig.shoppy.model.AdminAccountDto

interface AdminAccountService {

    fun getAllAccounts(): List<AdminAccountDto?>

    fun getAccountByLogin(
        login: String?
    ): AdminAccountDto

    fun addAccount(
        name: String?
    ): AdminAccountDto

    fun addAccount(
        dto: AdminAccountDto?
    ): AdminAccountDto

    fun updateAccount(
        dto: AdminAccountDto?
    ): AdminAccountDto

    fun deleteAccount(
        name: String?
    ): Int
}
