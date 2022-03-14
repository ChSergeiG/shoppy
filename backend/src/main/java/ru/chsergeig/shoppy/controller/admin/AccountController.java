package ru.chsergeig.shoppy.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.chsergeig.shoppy.dto.admin.AccountDto;
import ru.chsergeig.shoppy.service.admin.AccountService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/admin/account")
@RestController
@Secured("ROLE_ADMIN")
@CrossOrigin
public class AccountController {

    private final AccountService accountService;

    @GetMapping("get_all")
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        try {
            return ResponseEntity.ok(accountService.getAllAccounts());
        } catch (Exception e) {
            throw new ResponseStatusException(
                    499,
                    "Cant get accounts list: " + e.getLocalizedMessage(),
                    e
            );
        }
    }

    @PutMapping("{login}")
    public ResponseEntity<AccountDto> addDefaultAccount(
            @PathVariable String login
    ) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(accountService.addAccount(login));
        } catch (Exception e) {
            throw new ResponseStatusException(
                    499,
                    "Cant create new account: " + e.getLocalizedMessage(),
                    e
            );
        }
    }

    @PostMapping("add")
    public ResponseEntity<AccountDto> addAccountPost(
            @RequestBody AccountDto dto
    ) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(accountService.addAccount(dto));
        } catch (Exception e) {
            throw new ResponseStatusException(
                    499,
                    "Cant create new account: " + e.getLocalizedMessage(),
                    e
            );
        }
    }

    @PostMapping("update")
    public ResponseEntity<AccountDto> updateAccount(
            @RequestBody AccountDto dto
    ) {
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(accountService.updateAccount(dto));
        } catch (Exception e) {
            throw new ResponseStatusException(
                    499,
                    "Cant update account: " + e.getLocalizedMessage(),
                    e
            );
        }
    }

    @DeleteMapping("{login}")
    public ResponseEntity<Integer> deleteAccount(
            @PathVariable String login
    ) {
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(accountService.deleteAccount(login));
        } catch (Exception e) {
            throw new ResponseStatusException(
                    499,
                    "Cant delete account: " + e.getLocalizedMessage(),
                    e
            );
        }
    }
}
