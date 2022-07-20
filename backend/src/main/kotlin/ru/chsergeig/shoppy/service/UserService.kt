package ru.chsergeig.shoppy.service

import ru.chsergeig.shoppy.jooq.enums.AccountRole

interface UserService {

    fun getUserRoles(
        login: String
    ): List<AccountRole>
}
