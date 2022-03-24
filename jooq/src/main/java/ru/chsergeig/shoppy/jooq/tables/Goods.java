/*
 * This file is generated by jOOQ.
 */
package ru.chsergeig.shoppy.jooq.tables;


import java.math.BigDecimal;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row5;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;

import ru.chsergeig.shoppy.jooq.Keys;
import ru.chsergeig.shoppy.jooq.Public;
import ru.chsergeig.shoppy.jooq.enums.Status;
import ru.chsergeig.shoppy.jooq.tables.records.GoodsRecord;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Goods extends TableImpl<GoodsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.goods</code>
     */
    public static final Goods GOODS = new Goods();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<GoodsRecord> getRecordType() {
        return GoodsRecord.class;
    }

    /**
     * The column <code>public.goods.id</code>.
     */
    public final TableField<GoodsRecord, Integer> ID = createField(DSL.name("id"), SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>public.goods.name</code>.
     */
    public final TableField<GoodsRecord, String> NAME = createField(DSL.name("name"), SQLDataType.VARCHAR(1024).nullable(false), this, "");

    /**
     * The column <code>public.goods.article</code>.
     */
    public final TableField<GoodsRecord, String> ARTICLE = createField(DSL.name("article"), SQLDataType.VARCHAR(1024), this, "");

    /**
     * The column <code>public.goods.price</code>.
     */
    public final TableField<GoodsRecord, BigDecimal> PRICE = createField(DSL.name("price"), SQLDataType.NUMERIC.defaultValue(DSL.field("0.0", SQLDataType.NUMERIC)), this, "");

    /**
     * The column <code>public.goods.status</code>.
     */
    public final TableField<GoodsRecord, Status> STATUS = createField(DSL.name("status"), SQLDataType.VARCHAR.nullable(false).defaultValue(DSL.field("'ADDED'::status", SQLDataType.VARCHAR)).asEnumDataType(ru.chsergeig.shoppy.jooq.enums.Status.class), this, "");

    private Goods(Name alias, Table<GoodsRecord> aliased) {
        this(alias, aliased, null);
    }

    private Goods(Name alias, Table<GoodsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.goods</code> table reference
     */
    public Goods(String alias) {
        this(DSL.name(alias), GOODS);
    }

    /**
     * Create an aliased <code>public.goods</code> table reference
     */
    public Goods(Name alias) {
        this(alias, GOODS);
    }

    /**
     * Create a <code>public.goods</code> table reference
     */
    public Goods() {
        this(DSL.name("goods"), null);
    }

    public <O extends Record> Goods(Table<O> child, ForeignKey<O, GoodsRecord> key) {
        super(child, key, GOODS);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public Identity<GoodsRecord, Integer> getIdentity() {
        return (Identity<GoodsRecord, Integer>) super.getIdentity();
    }

    @Override
    public UniqueKey<GoodsRecord> getPrimaryKey() {
        return Keys.GOOD_PK;
    }

    @Override
    public Goods as(String alias) {
        return new Goods(DSL.name(alias), this);
    }

    @Override
    public Goods as(Name alias) {
        return new Goods(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Goods rename(String name) {
        return new Goods(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Goods rename(Name name) {
        return new Goods(name, null);
    }

    // -------------------------------------------------------------------------
    // Row5 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row5<Integer, String, String, BigDecimal, Status> fieldsRow() {
        return (Row5) super.fieldsRow();
    }
}
