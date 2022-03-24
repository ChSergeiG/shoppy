/*
 * This file is generated by jOOQ.
 */
package ru.chsergeig.shoppy.jooq.tables.records;


import java.math.BigDecimal;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record5;
import org.jooq.Row5;
import org.jooq.impl.UpdatableRecordImpl;

import ru.chsergeig.shoppy.jooq.enums.Status;
import ru.chsergeig.shoppy.jooq.tables.Goods;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class GoodsRecord extends UpdatableRecordImpl<GoodsRecord> implements Record5<Integer, String, String, BigDecimal, Status> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.goods.id</code>.
     */
    public GoodsRecord setId(Integer value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>public.goods.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.goods.name</code>.
     */
    public GoodsRecord setName(String value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>public.goods.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.goods.article</code>.
     */
    public GoodsRecord setArticle(String value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>public.goods.article</code>.
     */
    public String getArticle() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.goods.price</code>.
     */
    public GoodsRecord setPrice(BigDecimal value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>public.goods.price</code>.
     */
    public BigDecimal getPrice() {
        return (BigDecimal) get(3);
    }

    /**
     * Setter for <code>public.goods.status</code>.
     */
    public GoodsRecord setStatus(Status value) {
        set(4, value);
        return this;
    }

    /**
     * Getter for <code>public.goods.status</code>.
     */
    public Status getStatus() {
        return (Status) get(4);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record5 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row5<Integer, String, String, BigDecimal, Status> fieldsRow() {
        return (Row5) super.fieldsRow();
    }

    @Override
    public Row5<Integer, String, String, BigDecimal, Status> valuesRow() {
        return (Row5) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Goods.GOODS.ID;
    }

    @Override
    public Field<String> field2() {
        return Goods.GOODS.NAME;
    }

    @Override
    public Field<String> field3() {
        return Goods.GOODS.ARTICLE;
    }

    @Override
    public Field<BigDecimal> field4() {
        return Goods.GOODS.PRICE;
    }

    @Override
    public Field<Status> field5() {
        return Goods.GOODS.STATUS;
    }

    @Override
    public Integer component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getName();
    }

    @Override
    public String component3() {
        return getArticle();
    }

    @Override
    public BigDecimal component4() {
        return getPrice();
    }

    @Override
    public Status component5() {
        return getStatus();
    }

    @Override
    public Integer value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getName();
    }

    @Override
    public String value3() {
        return getArticle();
    }

    @Override
    public BigDecimal value4() {
        return getPrice();
    }

    @Override
    public Status value5() {
        return getStatus();
    }

    @Override
    public GoodsRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public GoodsRecord value2(String value) {
        setName(value);
        return this;
    }

    @Override
    public GoodsRecord value3(String value) {
        setArticle(value);
        return this;
    }

    @Override
    public GoodsRecord value4(BigDecimal value) {
        setPrice(value);
        return this;
    }

    @Override
    public GoodsRecord value5(Status value) {
        setStatus(value);
        return this;
    }

    @Override
    public GoodsRecord values(Integer value1, String value2, String value3, BigDecimal value4, Status value5) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached GoodsRecord
     */
    public GoodsRecord() {
        super(Goods.GOODS);
    }

    /**
     * Create a detached, initialised GoodsRecord
     */
    public GoodsRecord(Integer id, String name, String article, BigDecimal price, Status status) {
        super(Goods.GOODS);

        setId(id);
        setName(name);
        setArticle(article);
        setPrice(price);
        setStatus(status);
    }

    /**
     * Create a detached, initialised GoodsRecord
     */
    public GoodsRecord(ru.chsergeig.shoppy.jooq.tables.pojos.Goods value) {
        super(Goods.GOODS);

        if (value != null) {
            setId(value.getId());
            setName(value.getName());
            setArticle(value.getArticle());
            setPrice(value.getPrice());
            setStatus(value.getStatus());
        }
    }
}
