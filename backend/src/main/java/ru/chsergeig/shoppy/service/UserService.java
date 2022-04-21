package ru.chsergeig.shoppy.service;

import ru.chsergeig.shoppy.jooq.enums.AccountRole;

import java.util.List;

public interface UserService {

    List<AccountRole> getUserRoles(String login);

}
