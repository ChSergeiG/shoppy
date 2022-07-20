package ru.chsergeig.shoppy.impl.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chsergeig.shoppy.dao.AccountRepository;
import ru.chsergeig.shoppy.dao.AccountRoleRepository;
import ru.chsergeig.shoppy.dto.admin.AccountDto;
import ru.chsergeig.shoppy.jooq.enums.AccountRole;
import ru.chsergeig.shoppy.jooq.enums.Status;
import ru.chsergeig.shoppy.jooq.tables.pojos.Accounts;
import ru.chsergeig.shoppy.jooq.tables.pojos.AccountsRoles;
import ru.chsergeig.shoppy.mapping.AccountMapper;
import ru.chsergeig.shoppy.service.admin.AdminAccountService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ru.chsergeig.shoppy.jooq.Tables.ACCOUNTS;

@Service
@RequiredArgsConstructor
public class AdminAccountServiceImpl implements AdminAccountService {

    private final AccountRepository accountRepository;
    private final AccountRoleRepository accountRoleRepository;
    private final AccountMapper accountMapper;

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Accounts> accountPojos = accountRepository.fetch(
                ACCOUNTS.STATUS,
                Status.ADDED, Status.ACTIVE, Status.DISABLED
        );
        List<AccountDto> accountDtos = accountPojos.stream()
                .map(accountMapper::map)
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
                                .collect(Collectors.toList())
                )
        );
        return accountDtos;
    }

    @Override
    public AccountDto getAccountByLogin(String login) {
        List<Accounts> users = accountRepository.fetch(
                ACCOUNTS.LOGIN,
                login
        );
        AccountDto dto = users.isEmpty() ? null : accountMapper.map(users.get(0));
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
                        .collect(Collectors.toList())
        );
        return dto;
    }

    @Override
    @Transactional
    public AccountDto addAccount(String name) {
        Accounts pojo = new Accounts(null, name, null, false, Status.ADDED);
        accountRepository.insert(pojo);
        accountRoleRepository.insert(new AccountsRoles(pojo.getId(), AccountRole.ROLE_USER));
        AccountDto dto = accountMapper.map(pojo);
        dto.setAccountRoles(List.of(AccountRole.ROLE_USER));
        return dto;
    }

    @Override
    @Transactional
    public AccountDto addAccount(AccountDto dto) {
        Accounts pojo = accountMapper.map(dto);
        accountRepository.insert(pojo);
        accountRoleRepository.deleteByAccountId(pojo.getId());
        List<AccountsRoles> accountsRoles = dto.getAccountRoles().stream()
                .map(ar -> new AccountsRoles(pojo.getId(), ar))
                .collect(Collectors.toList());
        accountRoleRepository.insert(accountsRoles);
        AccountDto result = accountMapper.map(pojo);
        result.setAccountRoles(dto.getAccountRoles());
        return result;
    }

    @Override
    @Transactional
    public AccountDto updateAccount(AccountDto dto) {
        Accounts pojo = accountMapper.map(dto);
        accountRepository.update(pojo);
        accountRoleRepository.deleteByAccountId(pojo.getId());
        Set<AccountsRoles> accountsRoles = dto.getAccountRoles().stream()
                .map(ar -> new AccountsRoles(pojo.getId(), ar))
                .collect(Collectors.toSet());
        accountRoleRepository.insert(accountsRoles);
        AccountDto result = accountMapper.map(pojo);
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
