package ru.chsergeig.shoppy.dao

import org.jooq.Configuration
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import ru.chsergeig.shoppy.jooq.Tables.JWT_TOKENS
import ru.chsergeig.shoppy.jooq.enums.Status
import ru.chsergeig.shoppy.jooq.tables.pojos.JwtTokens
import java.time.OffsetDateTime

@Repository
class JwtTokenRepository @Autowired constructor(
    val configuration: Configuration
) {

    fun getToken(
        token: String
    ): JwtTokens? {
        return configuration.dsl()
            .selectFrom(JWT_TOKENS)
            .where(JWT_TOKENS.TOKEN.eq(token))
            .fetchOneInto(JwtTokens::class.java)
    }

    fun addToken(
        token: String,
        validUntil: OffsetDateTime = OffsetDateTime.now(),
        tokenStatus: Status = Status.ADDED
    ) {
        configuration.dsl()
            .insertInto(JWT_TOKENS)
            .columns(JWT_TOKENS.TOKEN, JWT_TOKENS.VALID_UNTIL, JWT_TOKENS.STATUS)
            .values(token, validUntil, tokenStatus)
            .execute()
    }

    fun setTokenRemoved(token: String) {
        configuration.dsl()
            .update(JWT_TOKENS)
            .set(JWT_TOKENS.STATUS, Status.REMOVED)
            .where(JWT_TOKENS.TOKEN.eq(token))
            .execute()
    }

    fun setTokenDisabled(token: String) {
        configuration.dsl()
            .update(JWT_TOKENS)
            .set(JWT_TOKENS.STATUS, Status.DISABLED)
            .where(JWT_TOKENS.TOKEN.eq(token))
            .execute()
    }

    fun setTokenActive(token: String) {
        configuration.dsl()
            .update(JWT_TOKENS)
            .set(JWT_TOKENS.STATUS, Status.ACTIVE)
            .where(JWT_TOKENS.TOKEN.eq(token))
            .execute()
    }

}