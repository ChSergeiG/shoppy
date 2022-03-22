package ru.chsergeig.shoppy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chsergeig.shoppy.dao.AccountRepository;
import ru.chsergeig.shoppy.dao.AccountRoleRepository;
import ru.chsergeig.shoppy.dto.admin.AccountDto;
import ru.chsergeig.shoppy.jooq.enums.Status;
import ru.chsergeig.shoppy.jooq.tables.pojos.Accounts;
import ru.chsergeig.shoppy.jooq.tables.pojos.AccountsRoles;
import ru.chsergeig.shoppy.mapping.AccountMapper;
import ru.chsergeig.shoppy.service.admin.AccountService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountRoleRepository accountRoleRepository;
    private final AccountMapper accountMapper;

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Accounts> accountPojos = accountRepository.fetchByStatus(Status.ADDED, Status.ACTIVE, Status.DISABLED);
        List<AccountDto> accountDtos = accountPojos.stream()
                .map(accountMapper::map)
                .collect(Collectors.toList());
        List<AccountsRoles> accountRoles = accountRoleRepository.fetchByAccountId(
                accountPojos.stream()
                        .map(Accounts::getId)
                        .toArray(Integer[]::new)
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
        List<Accounts> users = accountRepository.fetchByLogin(login);
        return users.isEmpty() ? null : accountMapper.map(users.get(0));
    }

    @Override
    public AccountDto addAccount(String name) {
        Accounts pojo = new Accounts(null, name, null, false, Status.ADDED);
        accountRepository.insert(pojo);
        return accountMapper.map(pojo);
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
        List<AccountsRoles> accountsRoles = dto.getAccountRoles().stream()
                .map(ar -> new AccountsRoles(pojo.getId(), ar))
                .collect(Collectors.toList());
        accountRoleRepository.insert(accountsRoles);
        AccountDto result = accountMapper.map(pojo);
        result.setAccountRoles(dto.getAccountRoles());
        return result;
    }

    @Override
    public Integer deleteAccount(String login) {
        List<Accounts> users = accountRepository.fetchByLogin(login);
        users.forEach(o -> o.setStatus(Status.REMOVED));
        accountRepository.update(users);
        return users.size();
    }
}
