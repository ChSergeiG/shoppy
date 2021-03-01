package ru.chsergeig.shoppy.controller;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
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
@RequestMapping("good")
@RestController
public class GoodController {

    private final DSLContext dsl;
    private final GoodMapper goodMapper;

    @GetMapping("get_all")
    public List<GoodDTO> getAll() {
        GoodRecord[] goods = dsl
                .selectFrom(GOOD)
                .where(GOOD.STATUS.eq(Status.ACTIVE))
                .fetchArray();
        return Arrays.stream(goods)
                .map(goodMapper::map)
                .collect(Collectors.toList());
    }

    @PostMapping("add")
    public void add(
            @RequestBody GoodDTO dto
    ) {
        dsl
                .insertInto(GOOD)
                .set(goodMapper.map(dto))
                .execute();

    }

    @PutMapping("delete/{id}")
    public void deleteById(
            @PathVariable Integer id
    ) {
        dsl
                .update(GOOD)
                .set(GOOD.STATUS, Status.REMOVED)
                .where(GOOD.ID.eq(id))
                .execute();
    }

}
