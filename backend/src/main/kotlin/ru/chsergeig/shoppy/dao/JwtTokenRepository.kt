package ru.chsergeig.shoppy.dao

import ru.chsergeig.shoppy.jooq.enums.Status
import ru.chsergeig.shoppy.jooq.tables.pojos.JwtTokens
import java.time.OffsetDateTime

interface JwtTokenRepository : JooqDao {

    fun getToken(
        token: String?
    ): JwtTokens?

    fun addToken(
        token: String?,
        validUntil: OffsetDateTime?,
        tokenStatus: Status?
    )

    fun setTokenRemoved(
        token: String?
    )

    fun setTokenDisabled(
        token: String?
    )

    fun setTokenActive(
        token: String?
    )
}
