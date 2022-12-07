package ru.chsergeig.shoppy.impl.admin;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chsergeig.shoppy.dao.AccountRepository;
import ru.chsergeig.shoppy.dao.AccountRoleRepository;
import ru.chsergeig.shoppy.jooq.enums.AccountRole;
import ru.chsergeig.shoppy.jooq.enums.Status;
import ru.chsergeig.shoppy.jooq.tables.pojos.Accounts;
import ru.chsergeig.shoppy.jooq.tables.pojos.AccountsRoles;
import ru.chsergeig.shoppy.mapping.AccountMapper;
import ru.chsergeig.shoppy.mapping.EnumMapper;
import ru.chsergeig.shoppy.model.AdminAccountDto;
import ru.chsergeig.shoppy.properties.SecurityProperties;
import ru.chsergeig.shoppy.service.admin.AdminAccountService;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static ru.chsergeig.shoppy.jooq.Tables.ACCOUNTS;

@Service
@RequiredArgsConstructor
public class AdminAccountServiceImpl implements AdminAccountService {

    private static final Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2(a|y|b)?\\$(\\d\\d)\\$[./0-9A-Za-z]{53}");

    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;
    private final AccountRoleRepository accountRoleRepository;
    private final EnumMapper enumMapper;
    private final PasswordEncoder passwordEncoder;
    private final SecurityProperties securityProperties;

    @NotNull
    @Override
    public List<AdminAccountDto> getAllAccounts() {
        List<Accounts> accountPojos = accountRepository.fetch(
                ACCOUNTS.STATUS,
                Status.ADDED, Status.ACTIVE, Status.DISABLED
        );
        List<AdminAccountDto> accountDtos = accountPojos.stream()
                .map(accountMapper::mapAdmin)
                .collect(Collectors.toList());
        List<AccountsRoles> accountRoles = accountRoleRepository.fetchByAccountId(
                accountPojos.stream()
                        .map(Accounts::getId)
                        .collect(Collectors.toList())
        );

        accountDtos.parallelStream().forEach(dto ->
                dto.setAccountRoles(
                        accountRoles.stream()
                                .filter(ar -> dto.getId().equals(ar.getAccountId()))
                                .map(AccountsRoles::getRole)
                                .map(enumMapper::fromJooq)
                                .collect(Collectors.toList())
                )
        );
        return accountDtos;
    }

    @Override
    public AdminAccountDto getAccountByLogin(String login) {
        List<Accounts> users = accountRepository.fetch(
                ACCOUNTS.LOGIN,
                login
        );
        AdminAccountDto dto = users.isEmpty() ? null : accountMapper.mapAdmin(users.get(0));
        if (dto == null) {
            return null;
        }
        List<AccountsRoles> accountRoles = accountRoleRepository.fetch(
                ACCOUNTS.ID,
                dto.getId()
        );
        dto.setAccountRoles(
                accountRoles.stream()
                        .map(AccountsRoles::getRole)
                        .map(enumMapper::fromJooq)
                        .collect(Collectors.toList())
        );
        return dto;
    }

    @Override
    @Transactional
    public AdminAccountDto addAccount(String name) {
        Accounts pojo = new Accounts(
                null,
                name,
                passwordEncoder.encode(securityProperties.getJwt().getSalt() + "$$" + UUID.randomUUID().toString()),
                true,
                Status.ADDED);
        accountRepository.insert(pojo);
        accountRoleRepository.insert(new AccountsRoles(pojo.getId(), AccountRole.ROLE_USER));
        AdminAccountDto dto = accountMapper.mapAdmin(pojo);
        dto.setAccountRoles(List.of(enumMapper.fromJooq(AccountRole.ROLE_USER)));
        return dto;
    }

    @Override
    @Transactional
    public AdminAccountDto addAccount(AdminAccountDto dto) {
        Accounts pojo = accountMapper.mapAdmin(dto);
        pojo.setPassword(passwordEncoder.encode(securityProperties.getJwt().getSalt() + "$$" + pojo.getPassword()));
        pojo.setSalted(true);
        accountRepository.insert(pojo);
        accountRoleRepository.deleteByAccountId(pojo.getId());
        List<AccountsRoles> accountsRoles = dto.getAccountRoles().stream()
                .map(ar -> new AccountsRoles(pojo.getId(), enumMapper.toJooq(ar)))
                .collect(Collectors.toList());
        accountRoleRepository.insert(accountsRoles);
        AdminAccountDto result = accountMapper.mapAdmin(pojo);
        result.setAccountRoles(dto.getAccountRoles());
        return result;
    }

    @Override
    @Transactional
    public AdminAccountDto updateAccount(AdminAccountDto dto) {
        Accounts pojo = accountMapper.mapAdmin(dto);
        if (dto.getPassword() != null && !BCRYPT_PATTERN.matcher(dto.getPassword()).matches()) {
            pojo.setPassword(passwordEncoder.encode(securityProperties.getJwt().getSalt() + "$$" + pojo.getPassword()));
            pojo.setSalted(true);
        }
        accountRepository.update(pojo);
        accountRoleRepository.deleteByAccountId(pojo.getId());
        Set<AccountsRoles> accountsRoles = dto.getAccountRoles().stream()
                .map(ar -> new AccountsRoles(pojo.getId(), enumMapper.toJooq(ar)))
                .collect(Collectors.toSet());
        accountRoleRepository.insert(accountsRoles);
        AdminAccountDto result = accountMapper.mapAdmin(pojo);
        result.setAccountRoles(dto.getAccountRoles());
        return result;
    }

    @Override
    public int deleteAccount(String login) {
        List<Accounts> users = accountRepository.fetch(ACCOUNTS.LOGIN, login);
        users.forEach(o -> o.setStatus(Status.REMOVED));
        accountRepository.update(users);
        return users.size();
    }
}
