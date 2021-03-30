package ru.chsergeig.shoppy.controller;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.chsergeig.shoppy.dto.GoodDTO;
import ru.chsergeig.shoppy.jooq.enums.Status;
import ru.chsergeig.shoppy.jooq.tables.records.GoodRecord;
import ru.chsergeig.shoppy.mapping.GoodMapper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ru.chsergeig.shoppy.jooq.tables.Good.GOOD;

@RequiredArgsConstructor
@RequestMapping("/admin/good")
@RestController
public class GoodController {

    private final DSLContext dsl;
    private final GoodMapper goodMapper;

    @GetMapping("get_all")
    public List<GoodDTO> getAllGoods() {
        GoodRecord[] usersRecords = dsl
                .selectFrom(GOOD)
                .where(GOOD.STATUS.notEqual(DSL.cast(Status.REMOVED, Status.class)))
                .fetchArray();
        return Arrays.stream(usersRecords)
                .map(goodMapper::map)
                .collect(Collectors.toList());
    }

    @PutMapping("{name}")
    public void addDefaultGood(
            @PathVariable String name
    ) {
        dsl
                .insertInto(GOOD)
                .set(GOOD.NAME, name)
                .set(GOOD.STATUS, DSL.cast(Status.ADDED, Status.class))
                .execute();
    }

    @PostMapping("add")
    public void addGoodPost(
            @RequestBody GoodDTO dto
    ) {
        dsl
                .insertInto(GOOD)
                .set(GOOD.NAME, dto.getName())
                .set(GOOD.ARTICLE, dto.getArticle())
                .set(GOOD.STATUS, DSL.cast(dto.getStatus(), Status.class))
                .execute();
    }

    @PostMapping("update")
    public String updateGood(
            @RequestBody GoodDTO dto
    ) {
        try {
            return "SUCCESS :" + dsl
                    .update(GOOD)
                    .set(goodMapper.map(dto))
                    .where(GOOD.ID.eq(dto.getId()))
                    .execute();
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
    }

    @DeleteMapping("{article}")
    public void deleteGood(
            @PathVariable Integer article
    ) {
        dsl
                .update(GOOD)
                .set(GOOD.STATUS, Status.REMOVED)
                .where(GOOD.ARTICLE.eq(article))
                .execute();
    }

}
