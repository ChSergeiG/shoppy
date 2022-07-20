package ru.chsergeig.shoppy.impl;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import ru.chsergeig.shoppy.jooq.enums.AccountRole;
import ru.chsergeig.shoppy.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl
        implements UserService {

    private final AccountRoleRepositoryImpl accountRoleRepository;

    @Override
    public List<AccountRole> getUserRoles(@Nullable String login) {
        return accountRoleRepository.fetchRolesByLogin(login);
    }

}
