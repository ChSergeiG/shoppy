package ru.chsergeig.shoppy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.chsergeig.shoppy.jooq.enums.AccountRole;
import ru.chsergeig.shoppy.jooq.enums.Status;

@RequiredArgsConstructor
@RequestMapping("/common")
@RestController
@CrossOrigin
public class CommonController {

    @Operation(
            summary = "Get available status to assign entity",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All good")
            }
    )
    @GetMapping("enum/statuses")
    public ResponseEntity<Status[]> getAllStatuses() {
        return ResponseEntity.ok(Status.values());
    }

    @Operation(
            summary = "Get available role to assign account",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All good")
            }
    )
    @GetMapping("enum/account-roles")
    public ResponseEntity<AccountRole[]> getAllRoles() {
        return ResponseEntity.ok(AccountRole.values());
    }

}
