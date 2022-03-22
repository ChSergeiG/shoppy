package ru.chsergeig.shoppy.dto.admin;

import lombok.Data;
import ru.chsergeig.shoppy.jooq.enums.AccountRole;
import ru.chsergeig.shoppy.jooq.enums.Status;

import java.util.List;

@Data
public class AccountDto {

    private Integer id;
    private String login;
    private String password;
    private Boolean salted;
    private Status status;
    private List<AccountRole> accountRoles;

}
