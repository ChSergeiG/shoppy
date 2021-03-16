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
import ru.chsergeig.shoppy.dto.UserDTO;
import ru.chsergeig.shoppy.jooq.enums.Status;
import ru.chsergeig.shoppy.jooq.tables.records.UserRecord;
import ru.chsergeig.shoppy.mapping.UserMapper;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static ru.chsergeig.shoppy.jooq.tables.User.USER;

@RequiredArgsConstructor
@RequestMapping("users")
@RestController
public class UserController {

    private final DSLContext dsl;
    private final UserMapper userMapper;

    @GetMapping("get_all")
    public List<UserDTO> getAllUsers() {
        UserRecord[] usersRecords = dsl
                .selectFrom(USER)
                .where(USER.STATUS.eq(DSL.cast(Status.ACTIVE, USER.STATUS)))
                .fetchArray();
        return Arrays.stream(usersRecords)
                .map(userMapper::map)
                .collect(Collectors.toList());
    }

    @PutMapping("{name}")
    public void addDefaultUser(
            @PathVariable String name
    ) {
        dsl
                .insertInto(USER)
                .set(USER.NAME, name)
                .set(USER.PASSWORD, UUID.randomUUID().toString())
                .set(USER.STATUS, DSL.cast(Status.ADDED, Status.class))
                .execute();
    }

    @PostMapping("add")
    public void addUserPost(
            @RequestBody UserDTO dto
    ) {
        dsl
                .insertInto(USER)
                .set(userMapper.map(dto))
                .execute();
    }

    @PostMapping("activate/{name}")
    public void activateUser(@PathVariable String name) {
        dsl
                .update(USER)
                .set(USER.STATUS, Status.ADDED)
                .where(USER.NAME.eq(name).and(USER.STATUS.notEqual(DSL.cast(Status.ACTIVE, Status.class))))
                .execute();
    }

    @DeleteMapping("{name}")
    public void deleteUser(@PathVariable String name) {
        dsl
                .update(USER)
                .set(USER.STATUS,  Status.REMOVED )
                .where(USER.NAME.eq(name))
                .execute();
    }

}
