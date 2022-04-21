package ru.chsergeig.shoppy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.chsergeig.shoppy.component.TokenUtilComponent;
import ru.chsergeig.shoppy.jooq.enums.AccountRole;
import ru.chsergeig.shoppy.properties.SecurityProperties;
import ru.chsergeig.shoppy.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
@CrossOrigin
public class UserController {

    private final SecurityProperties securityProperties;
    private final TokenUtilComponent tokenUtilComponent;
    private final UserService userService;

    @Operation(
            summary = "Get user roles",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User roles")
            }
    )
    @GetMapping("roles")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<List<AccountRole>> getUserRoles(
            HttpServletRequest httpServletRequest
    ) {
        String token = httpServletRequest.getHeader(securityProperties.getJwt().getAuthorizationHeader());
        if (!StringUtils.hasText(token)) {
            return ResponseEntity.ok(List.of());
        }
        String login = tokenUtilComponent.getUsernameFromToken(token);
        List<AccountRole> roles = userService.getUserRoles(login);
        return ResponseEntity.ok(roles);
    }

    @Operation(
            summary = "Get user roles",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User roles")
            }
    )
    @GetMapping("roles/{login}")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<List<AccountRole>> getUserRoles(
            @PathVariable("login") String login
    ) {
        List<AccountRole> roles = userService.getUserRoles(login);
        return ResponseEntity.ok(roles);
    }

}
