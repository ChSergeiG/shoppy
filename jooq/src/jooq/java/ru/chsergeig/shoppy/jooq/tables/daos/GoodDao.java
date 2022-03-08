/*
 * This file is generated by jOOQ.
 */
package ru.chsergeig.shoppy.jooq.tables.daos;


import java.util.List;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;

import ru.chsergeig.shoppy.jooq.enums.Status;
import ru.chsergeig.shoppy.jooq.tables.Good;
import ru.chsergeig.shoppy.jooq.tables.records.GoodRecord;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class GoodDao extends DAOImpl<GoodRecord, ru.chsergeig.shoppy.jooq.tables.pojos.Good, Integer> {

    /**
     * Create a new GoodDao without any configuration
     */
    public GoodDao() {
        super(Good.GOOD, ru.chsergeig.shoppy.jooq.tables.pojos.Good.class);
    }

    /**
     * Create a new GoodDao with an attached configuration
     */
    public GoodDao(Configuration configuration) {
        super(Good.GOOD, ru.chsergeig.shoppy.jooq.tables.pojos.Good.class, configuration);
    }

    @Override
    public Integer getId(ru.chsergeig.shoppy.jooq.tables.pojos.Good object) {
        return object.getId();
    }

    /**
     * Fetch records that have <code>id BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<ru.chsergeig.shoppy.jooq.tables.pojos.Good> fetchRangeOfId(Integer lowerInclusive, Integer upperInclusive) {
        return fetchRange(Good.GOOD.ID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public List<ru.chsergeig.shoppy.jooq.tables.pojos.Good> fetchById(Integer... values) {
        return fetch(Good.GOOD.ID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public ru.chsergeig.shoppy.jooq.tables.pojos.Good fetchOneById(Integer value) {
        return fetchOne(Good.GOOD.ID, value);
    }

    /**
     * Fetch records that have <code>name BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<ru.chsergeig.shoppy.jooq.tables.pojos.Good> fetchRangeOfName(String lowerInclusive, String upperInclusive) {
        return fetchRange(Good.GOOD.NAME, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>name IN (values)</code>
     */
    public List<ru.chsergeig.shoppy.jooq.tables.pojos.Good> fetchByName(String... values) {
        return fetch(Good.GOOD.NAME, values);
    }

    /**
     * Fetch records that have <code>article BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<ru.chsergeig.shoppy.jooq.tables.pojos.Good> fetchRangeOfArticle(Integer lowerInclusive, Integer upperInclusive) {
        return fetchRange(Good.GOOD.ARTICLE, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>article IN (values)</code>
     */
    public List<ru.chsergeig.shoppy.jooq.tables.pojos.Good> fetchByArticle(Integer... values) {
        return fetch(Good.GOOD.ARTICLE, values);
    }

    /**
     * Fetch records that have <code>status BETWEEN lowerInclusive AND upperInclusive</code>
     */
    public List<ru.chsergeig.shoppy.jooq.tables.pojos.Good> fetchRangeOfStatus(Status lowerInclusive, Status upperInclusive) {
        return fetchRange(Good.GOOD.STATUS, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>status IN (values)</code>
     */
    public List<ru.chsergeig.shoppy.jooq.tables.pojos.Good> fetchByStatus(Status... values) {
        return fetch(Good.GOOD.STATUS, values);
    }
}