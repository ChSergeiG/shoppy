package ru.chsergeig.shoppy.controller;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.chsergeig.shoppy.dto.OrderDTO;
import ru.chsergeig.shoppy.jooq.enums.Status;
import ru.chsergeig.shoppy.jooq.tables.records.OrderRecord;
import ru.chsergeig.shoppy.mapping.OrderMapper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ru.chsergeig.shoppy.jooq.tables.Order.ORDER;

@RequiredArgsConstructor
@RequestMapping("order")
@RestController
public class OrderController {

    private final DSLContext dsl;
    private final OrderMapper orderMapper;


    @GetMapping("get_all")
    public List<OrderDTO> getAll() {
        OrderRecord[] orders = dsl
                .selectFrom(ORDER)
                .where(ORDER.STATUS.eq(DSL.cast(Status.ACTIVE, Status.class)))
                .fetchArray();
        return Arrays.stream(orders)
                .map(orderMapper::map)
                .collect(Collectors.toList());
    }

    @PostMapping("add")
    public void add(
            @RequestBody OrderDTO dto
    ) {
        dsl
                .insertInto(ORDER)
                .set(orderMapper.map(dto))
                .execute();

    }

    @PutMapping("delete/{id}")
    public void deleteById(
            @PathVariable Integer id
    ) {
        dsl
                .update(ORDER)
                .set(ORDER.STATUS, DSL.cast(Status.REMOVED, Status.class))
                .where(ORDER.ID.eq(id))
                .execute();
    }

}
