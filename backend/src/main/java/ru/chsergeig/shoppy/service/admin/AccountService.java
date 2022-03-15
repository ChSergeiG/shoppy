package ru.chsergeig.shoppy.service.admin;

import ru.chsergeig.shoppy.dto.admin.AccountDto;

import java.util.List;

public interface AccountService {

    List<AccountDto> getAllAccounts();

    AccountDto addAccount(String name);

    AccountDto addAccount(AccountDto dto);

    AccountDto updateAccount(AccountDto dto);

    Integer deleteAccount(String name);

}