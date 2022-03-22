/*
 * This file is generated by jOOQ.
 */
package ru.chsergeig.shoppy.jooq.tables.daos;


import java.util.List;

import org.jooq.Configuration;
import org.jooq.Record2;
import org.jooq.impl.DAOImpl;

import ru.chsergeig.shoppy.jooq.tables.OrdersGoods;
import ru.chsergeig.shoppy.jooq.tables.records.OrdersGoodsRecord;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class OrdersGoodsDao extends DAOImpl<OrdersGoodsRecord, ru.chsergeig.shoppy.jooq.tables.pojos.OrdersGoods, Record2<Integer, Integer>> {

    /**
     * Create a new OrdersGoodsDao without any configuration
     */
    public OrdersGoodsDao() {
        super(OrdersGoods.ORDERS_GOODS, ru.chsergeig.shoppy.jooq.tables.pojos.OrdersGoods.class);
    }

    /**
     * Create a new OrdersGoodsDao with an attached configuration
     */
    public OrdersGoodsDao(Configuration configuration) {
        super(OrdersGoods.ORDERS_GOODS, ru.chsergeig.shoppy.jooq.tables.pojos.OrdersGoods.class, configuration);
    }

    @Override
    public Record2<Integer, Integer> getId(ru.chsergeig.shoppy.jooq.tables.pojos.OrdersGoods object) {
        return compositeKeyRecord(object.getOrderId(), object.getGoodId());
    }

    /**
     * Fetch records that have <code>order_id BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<ru.chsergeig.shoppy.jooq.tables.pojos.OrdersGoods> fetchRangeOfOrderId(Integer lowerInclusive, Integer upperInclusive) {
        return fetchRange(OrdersGoods.ORDERS_GOODS.ORDER_ID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>order_id IN (values)</code>
     */
    public List<ru.chsergeig.shoppy.jooq.tables.pojos.OrdersGoods> fetchByOrderId(Integer... values) {
        return fetch(OrdersGoods.ORDERS_GOODS.ORDER_ID, values);
    }

    /**
     * Fetch records that have <code>good_id BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<ru.chsergeig.shoppy.jooq.tables.pojos.OrdersGoods> fetchRangeOfGoodId(Integer lowerInclusive, Integer upperInclusive) {
        return fetchRange(OrdersGoods.ORDERS_GOODS.GOOD_ID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>good_id IN (values)</code>
     */
    public List<ru.chsergeig.shoppy.jooq.tables.pojos.OrdersGoods> fetchByGoodId(Integer... values) {
        return fetch(OrdersGoods.ORDERS_GOODS.GOOD_ID, values);
    }

    /**
     * Fetch records that have <code>count BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<ru.chsergeig.shoppy.jooq.tables.pojos.OrdersGoods> fetchRangeOfCount(Long lowerInclusive, Long upperInclusive) {
        return fetchRange(OrdersGoods.ORDERS_GOODS.COUNT, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>count IN (values)</code>
     */
    public List<ru.chsergeig.shoppy.jooq.tables.pojos.OrdersGoods> fetchByCount(Long... values) {
        return fetch(OrdersGoods.ORDERS_GOODS.COUNT, values);
    }
}
