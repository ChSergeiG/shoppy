package ru.chsergeig.shoppy.service;

import ru.chsergeig.shoppy.dto.AccountDto;

import java.util.List;

public interface AccountService {

    List<AccountDto> getAllAccounts();

    AccountDto addAccount(String name);

    AccountDto addAccount(AccountDto dto);

    AccountDto updateAccount(AccountDto dto);

    Integer deleteAccount(String name);

}
