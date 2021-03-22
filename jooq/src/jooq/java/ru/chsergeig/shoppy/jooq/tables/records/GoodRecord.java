/*
 * This file is generated by jOOQ.
 */
package ru.chsergeig.shoppy.jooq.tables.records;


import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;

import ru.chsergeig.shoppy.jooq.enums.Status;
import ru.chsergeig.shoppy.jooq.tables.Good;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class GoodRecord extends UpdatableRecordImpl<GoodRecord> implements Record4<Integer, String, Integer, Status> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.good.id</code>.
     */
    public GoodRecord setId(Integer value) {
        set(0, value);
        return this;
    }

    /**
     * Getter for <code>public.good.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.good.name</code>.
     */
    public GoodRecord setName(String value) {
        set(1, value);
        return this;
    }

    /**
     * Getter for <code>public.good.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.good.article</code>.
     */
    public GoodRecord setArticle(Integer value) {
        set(2, value);
        return this;
    }

    /**
     * Getter for <code>public.good.article</code>.
     */
    public Integer getArticle() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>public.good.status</code>.
     */
    public GoodRecord setStatus(Status value) {
        set(3, value);
        return this;
    }

    /**
     * Getter for <code>public.good.status</code>.
     */
    public Status getStatus() {
        return (Status) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row4<Integer, String, Integer, Status> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    @Override
    public Row4<Integer, String, Integer, Status> valuesRow() {
        return (Row4) super.valuesRow();
    }

    @Override
    public Field<Integer> field1() {
        return Good.GOOD.ID;
    }

    @Override
    public Field<String> field2() {
        return Good.GOOD.NAME;
    }

    @Override
    public Field<Integer> field3() {
        return Good.GOOD.ARTICLE;
    }

    @Override
    public Field<Status> field4() {
        return Good.GOOD.STATUS;
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
    public Integer component3() {
        return getArticle();
    }

    @Override
    public Status component4() {
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
    public Integer value3() {
        return getArticle();
    }

    @Override
    public Status value4() {
        return getStatus();
    }

    @Override
    public GoodRecord value1(Integer value) {
        setId(value);
        return this;
    }

    @Override
    public GoodRecord value2(String value) {
        setName(value);
        return this;
    }

    @Override
    public GoodRecord value3(Integer value) {
        setArticle(value);
        return this;
    }

    @Override
    public GoodRecord value4(Status value) {
        setStatus(value);
        return this;
    }

    @Override
    public GoodRecord values(Integer value1, String value2, Integer value3, Status value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached GoodRecord
     */
    public GoodRecord() {
        super(Good.GOOD);
    }

    /**
     * Create a detached, initialised GoodRecord
     */
    public GoodRecord(Integer id, String name, Integer article, Status status) {
        super(Good.GOOD);

        setId(id);
        setName(name);
        setArticle(article);
        setStatus(status);
    }
}