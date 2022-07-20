package ru.chsergeig.shoppy.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
import ru.chsergeig.shoppy.component.TokenUtilComponent;
import ru.chsergeig.shoppy.dto.admin.AccountDto;
import ru.chsergeig.shoppy.exception.ControllerException;
import ru.chsergeig.shoppy.properties.SecurityProperties;
import ru.chsergeig.shoppy.service.admin.AdminAccountService;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/admin/account")
@RestController
@Secured("ROLE_ADMIN")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminAccountController {

    private final AdminAccountService adminAccountService;
    private final SecurityProperties securityProperties;
    private final TokenUtilComponent tokenUtilComponent;

    @Operation(
            summary = "Get all not removed accounts",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All good"),
                    @ApiResponse(responseCode = "401", description = "Need authorization"),
                    @ApiResponse(responseCode = "499", description = "Error in backend"),
                    @ApiResponse(responseCode = "500", description = "Error in wrapper")
            }
    )
    @GetMapping("get_all")
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        try {
            return ResponseEntity.ok(adminAccountService.getAllAccounts());
        } catch (Exception e) {
            throw new ControllerException(
                    499,
                    "Cant get accounts list",
                    e
            );
        }
    }

    @Operation(
            summary = "Get account by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All good"),
                    @ApiResponse(responseCode = "401", description = "Need authorization"),
                    @ApiResponse(responseCode = "499", description = "Error in backend"),
                    @ApiResponse(responseCode = "500", description = "Error in wrapper")
            }
    )
    @GetMapping("{login}")
    public ResponseEntity<AccountDto> getAccount(
            @PathVariable("login") String login
    ) {
        try {
            return ResponseEntity.ok(adminAccountService.getAccountByLogin(login));
        } catch (Exception e) {
            throw new ControllerException(
                    499,
                    "Cant get account by id",
                    e
            );
        }
    }

    @Operation(
            summary = "Create default account",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Account created"),
                    @ApiResponse(responseCode = "401", description = "Need authorization"),
                    @ApiResponse(responseCode = "499", description = "Cant create account"),
                    @ApiResponse(responseCode = "500", description = "Error in wrapper")
            }
    )
    @PutMapping("{login}")
    public ResponseEntity<AccountDto> addDefaultAccount(
            @PathVariable String login
    ) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(adminAccountService.addAccount(login));
        } catch (Exception e) {
            throw new ControllerException(
                    499,
                    "Cant create new account",
                    e
            );
        }
    }

    @Operation(
            summary = "Create account",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Account created"),
                    @ApiResponse(responseCode = "401", description = "Need authorization"),
                    @ApiResponse(responseCode = "499", description = "Cant create account"),
                    @ApiResponse(responseCode = "500", description = "Error in wrapper")
            }
    )
    @PostMapping("add")
    public ResponseEntity<AccountDto> addAccountPost(
            @RequestBody AccountDto dto
    ) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(adminAccountService.addAccount(dto));
        } catch (Exception e) {
            throw new ControllerException(
                    499,
                    "Cant create new account",
                    e
            );
        }
    }

    @Operation(
            summary = "Update existing account",
            responses = {
                    @ApiResponse(responseCode = "202", description = "Account updated"),
                    @ApiResponse(responseCode = "401", description = "Need authorization"),
                    @ApiResponse(responseCode = "499", description = "Cant update account"),
                    @ApiResponse(responseCode = "500", description = "Error in wrapper")
            }
    )
    @PostMapping("update")
    public ResponseEntity<AccountDto> updateAccount(
            HttpServletRequest httpServletRequest,
            @RequestBody AccountDto dto
    ) {
        try {
            if (isSelfModification(httpServletRequest, dto.getLogin())) {
                AccountDto existingAccount = adminAccountService.getAccountByLogin(dto.getLogin());
                dto.setAccountRoles(
                        existingAccount.getAccountRoles()
                );
                dto.setStatus(existingAccount.getStatus());
            }
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(adminAccountService.updateAccount(dto));
        } catch (Exception e) {
            throw new ControllerException(
                    499,
                    "Cant update account",
                    e
            );
        }
    }

    @Operation(
            summary = "Delete existing account",
            responses = {
                    @ApiResponse(responseCode = "202", description = "Account deleted"),
                    @ApiResponse(responseCode = "401", description = "Need authorization"),
                    @ApiResponse(responseCode = "499", description = "Cant create account"),
                    @ApiResponse(responseCode = "500", description = "Error in wrapper")
            }
    )
    @DeleteMapping("{login}")
    public ResponseEntity<Integer> deleteAccount(
            HttpServletRequest httpServletRequest,
            @PathVariable String login
    ) {
        if (isSelfModification(httpServletRequest, login)) {
            throw new ControllerException(
                    HttpStatus.NOT_MODIFIED,
                    "Self modification is not allowed",
                    null
            );
        }
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(adminAccountService.deleteAccount(login));
        } catch (Exception e) {
            throw new ControllerException(
                    499,
                    "Cant delete account",
                    e
            );
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private boolean isSelfModification(HttpServletRequest httpServletRequest, String login) {
        final String token = httpServletRequest.getHeader(securityProperties.getJwt().getAuthorizationHeader());
        return login.equals(tokenUtilComponent.validateTokenAndGetUsername(token));
    }

}
