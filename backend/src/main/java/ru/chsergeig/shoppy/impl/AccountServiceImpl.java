package ru.chsergeig.shoppy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.chsergeig.shoppy.dao.AccountRepository;
import ru.chsergeig.shoppy.dto.admin.AccountDto;
import ru.chsergeig.shoppy.jooq.enums.Status;
import ru.chsergeig.shoppy.jooq.tables.pojos.Accounts;
import ru.chsergeig.shoppy.mapping.AccountMapper;
import ru.chsergeig.shoppy.service.admin.AccountService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Accounts> users = accountRepository.fetchByStatus(Status.ADDED, Status.ACTIVE, Status.DISABLED);
        return users.stream()
                .map(accountMapper::map)
                .collect(Collectors.toList());
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
    public AccountDto addAccount(AccountDto dto) {
        Accounts pojo = accountMapper.map(dto);
        accountRepository.insert(pojo);
        return accountMapper.map(pojo);
    }

    @Override
    public AccountDto updateAccount(AccountDto dto) {
        Accounts pojo = accountMapper.map(dto);
        accountRepository.update(pojo);
        return accountMapper.map(pojo);
    }

    @Override
    public Integer deleteAccount(String login) {
        List<Accounts> users = accountRepository.fetchByLogin(login);
        users.forEach(o -> o.setStatus(Status.REMOVED));
        accountRepository.update(users);
        return users.size();
    }
}
