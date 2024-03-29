/*
 * This file is generated by jOOQ.
 */
package ru.chsergeig.shoppy.jooq.tables.records;


import org.jooq.Field;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.TableRecordImpl;
import ru.chsergeig.shoppy.jooq.enums.Status;
import ru.chsergeig.shoppy.jooq.tables.JwtTokens;

import java.time.OffsetDateTime;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class JwtTokensRecord extends TableRecordImpl<JwtTokensRecord> implements Record3<String, OffsetDateTime, Status> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.jwt_tokens.token</code>. Token sha256
     */
    public JwtTokensRecord setToken(String value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>public.jwt_tokens.token</code>. Token sha256
     */
    public String getToken() {
        return (String) get(0);
    }

    /**
     * Setter for <code>public.jwt_tokens.valid_until</code>. Token expiration date
     */
    public JwtTokensRecord setValidUntil(OffsetDateTime value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>public.jwt_tokens.valid_until</code>. Token expiration date
     */
    public OffsetDateTime getValidUntil() {
        return (OffsetDateTime) get(1);
    }

    /**
     * Setter for <code>public.jwt_tokens.status</code>. Token status
     */
    public JwtTokensRecord setStatus(Status value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>public.jwt_tokens.status</code>. Token status
     */
    public Status getStatus() {
        return (Status) get(2);
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row3<String, OffsetDateTime, Status> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<String, OffsetDateTime, Status> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return JwtTokens.JWT_TOKENS.TOKEN;
    }

    @Override
    public Field<OffsetDateTime> field2() {
        return JwtTokens.JWT_TOKENS.VALID_UNTIL;
    }

    @Override
    public Field<Status> field3() {
        return JwtTokens.JWT_TOKENS.STATUS;
    }

    @Override
    public String component1() {
        return getToken();
    }

    @Override
    public OffsetDateTime component2() {
        return getValidUntil();
    }

    @Override
    public Status component3() {
        return getStatus();
    }

    @Override
    public String value1() {
        return getToken();
    }

    @Override
    public OffsetDateTime value2() {
        return getValidUntil();
    }

    @Override
    public Status value3() {
        return getStatus();
    }

    @Override
    public JwtTokensRecord value1(String value) {
        setToken(value);
        return this;
    }

    @Override
    public JwtTokensRecord value2(OffsetDateTime value) {
        setValidUntil(value);
        return this;
    }

    @Override
    public JwtTokensRecord value3(Status value) {
        setStatus(value);
        return this;
    }

    @Override
    public JwtTokensRecord values(String value1, OffsetDateTime value2, Status value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached JwtTokensRecord
     */
    public JwtTokensRecord() {
        super(JwtTokens.JWT_TOKENS);
    }

    /**
     * Create a detached, initialised JwtTokensRecord
     */
    public JwtTokensRecord(String token, OffsetDateTime validUntil, Status status) {
        super(JwtTokens.JWT_TOKENS);

        setToken(token);
        setValidUntil(validUntil);
        setStatus(status);
    }
}
