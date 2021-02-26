package ru.chsergeig.shoppy.controller;

import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.chsergeig.shoppy.dto.UserDTO;
import ru.chsergeig.shoppy.jooq.tables.Users;
import ru.chsergeig.shoppy.jooq.tables.records.UsersRecord;
import ru.chsergeig.shoppy.mapping.UserMapper;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("users")
@RestController
public class UsersController {

    private final DSLContext dsl;
    private final UserMapper userMapper;

    @GetMapping("get_all")
    public List<UserDTO> getAllUsers() {
        UsersRecord[] usersRecords = dsl
                .selectFrom(Users.USERS)
                .fetchArray();
        return Arrays.stream(usersRecords)
                .map(userMapper::recordToDto)
                .collect(Collectors.toList());
    }

    @PutMapping("add/{name}")
    public void addUser (@PathVariable String name) {
        dsl.insertInto(Users.USERS)
                .set(Users.USERS.NAME, name)
                .set(Users.USERS.PASSWORD, UUID.randomUUID().toString())
                .execute();
    }

    @DeleteMapping("delete/{name}")
    public void deleteUser (@PathVariable String name) {
        dsl.deleteFrom(Users.USERS)
                .where(Users.USERS.NAME.eq(name))
                .execute();
    }

}
