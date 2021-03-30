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
import ru.chsergeig.shoppy.dto.OrderDTO;
import ru.chsergeig.shoppy.jooq.enums.Status;
import ru.chsergeig.shoppy.jooq.tables.records.OrderRecord;
import ru.chsergeig.shoppy.mapping.OrderMapper;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ru.chsergeig.shoppy.jooq.tables.Order.ORDER;

@RequiredArgsConstructor
@RequestMapping("/admin/order")
@RestController
public class OrderController {

    private final DSLContext dsl;
    private final OrderMapper orderMapper;

    @GetMapping("get_all")
    public List<OrderDTO> getAllOrders() {
        OrderRecord[] usersRecords = dsl
                .selectFrom(ORDER)
                .where(ORDER.STATUS.notEqual(DSL.cast(Status.REMOVED, Status.class)))
                .fetchArray();
        return Arrays.stream(usersRecords)
                .map(orderMapper::map)
                .collect(Collectors.toList());
    }

    @PutMapping("{info}")
    public void addDefaultOrder(
             @PathVariable String info
    ) {
        dsl
                .insertInto(ORDER)
                .set(ORDER.INFO, info)
                .set(ORDER.STATUS, DSL.cast(Status.ADDED, Status.class))
                .execute();
    }

    @PostMapping("add")
    public void addOrderPost(
            @RequestBody OrderDTO dto
    ) {
        dsl
                .insertInto(ORDER)
                .set(ORDER.INFO, dto.getInfo())
                .set(ORDER.STATUS, DSL.cast(dto.getStatus(), Status.class))
                .execute();
    }

    @PostMapping("update")
    public String updateOrder(
            @RequestBody OrderDTO dto
    ) {
        try {
            return "SUCCESS :" + dsl
                    .update(ORDER)
                    .set(orderMapper.map(dto))
                    .where(ORDER.ID.eq(dto.getId()))
                    .execute();
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
    }

    @DeleteMapping("{id}")
    public void deleteOrder(
            @PathVariable Integer id
    ) {
        dsl
                .update(ORDER)
                .set(ORDER.STATUS, Status.REMOVED)
                .where(ORDER.ID.eq(id))
                .execute();
    }

}
