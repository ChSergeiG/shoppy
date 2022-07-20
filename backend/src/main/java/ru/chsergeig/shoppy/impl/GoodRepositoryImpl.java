package ru.chsergeig.shoppy.impl;

import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import ru.chsergeig.shoppy.dao.GoodRepository;
import ru.chsergeig.shoppy.jooq.enums.Status;
import ru.chsergeig.shoppy.jooq.tables.daos.GoodsDao;
import ru.chsergeig.shoppy.jooq.tables.pojos.Goods;

import java.util.ArrayList;
import java.util.List;

import static ru.chsergeig.shoppy.jooq.Tables.GOODS;

@Repository
public class GoodRepositoryImpl
        extends GoodsDao
        implements GoodRepository {

    public GoodRepositoryImpl(@Autowired Configuration configuration) {
        super(configuration);
    }

    @Override
    public List<Goods> fetchByFilterAndPagination(String filter, Pageable pageable) {
        List<Condition> conditions = new ArrayList<>();
        if (StringUtils.hasText(filter)) {
            conditions.add(GOODS.NAME.equalIgnoreCase(filter.trim()));
            conditions.add(GOODS.ARTICLE.likeIgnoreCase(filter.trim()));
        }
        SelectConditionStep<Record> selectConditionStep = ctx().select()
                .from(GOODS)
                .where(conditions);
        if (pageable != null) {
            return selectConditionStep
                    .limit(pageable.getPageSize())
                    .offset(pageable.getOffset())
                    .fetchInto(Goods.class);
        }
        return selectConditionStep
                .fetchInto(Goods.class);
    }

    @Override
    public int countActive() {
        return ctx().select(DSL.count())
                .from(GOODS)
                .where(GOODS.STATUS.eq(Status.ACTIVE))
                .fetchOneInto(Integer.class);
    }

}
