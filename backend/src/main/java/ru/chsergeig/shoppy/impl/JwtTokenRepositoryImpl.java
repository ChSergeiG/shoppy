package ru.chsergeig.shoppy.impl;


import org.jetbrains.annotations.Nullable;
import org.jooq.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.chsergeig.shoppy.dao.JwtTokenRepository;
import ru.chsergeig.shoppy.jooq.enums.Status;
import ru.chsergeig.shoppy.jooq.tables.pojos.JwtTokens;

import java.time.OffsetDateTime;

import static ru.chsergeig.shoppy.jooq.Tables.JWT_TOKENS;

@Repository
public class JwtTokenRepositoryImpl
        extends JooqDaoImpl
        implements JwtTokenRepository {

    @Autowired
    public JwtTokenRepositoryImpl(
            Configuration configuration
    ) {
        super(configuration);
    }

    @Override
    @Nullable
    public JwtTokens getToken(
            @Nullable String token
    ) {
        return ctx()
                .selectFrom(JWT_TOKENS)
                .where(JWT_TOKENS.TOKEN.eq(token))
                .fetchOneInto(JwtTokens.class);
    }

    public void addToken(
            String token,
            OffsetDateTime validUntil,
            Status tokenStatus
    ) {
        ctx()
                .insertInto(JWT_TOKENS)
                .columns(JWT_TOKENS.TOKEN, JWT_TOKENS.VALID_UNTIL, JWT_TOKENS.STATUS)
                .values(token, validUntil, tokenStatus)
                .execute();
    }

    public void setTokenRemoved(String token) {
        setTokenStatus(token, Status.REMOVED);
    }

    public void setTokenDisabled(String token) {
        setTokenStatus(token, Status.DISABLED);
    }

    public void setTokenActive(String token) {
        setTokenStatus(token, Status.ACTIVE);
    }

    private void setTokenStatus(String token, Status status) {
        if (token == null || status == null) {
            return;
        }
        ctx()
                .update(JWT_TOKENS)
                .set(JWT_TOKENS.STATUS, status)
                .where(JWT_TOKENS.TOKEN.eq(token))
                .execute();
    }

}
