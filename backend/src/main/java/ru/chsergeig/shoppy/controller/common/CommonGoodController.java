package ru.chsergeig.shoppy.controller.common;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.chsergeig.shoppy.component.TokenUtilComponent;
import ru.chsergeig.shoppy.dto.CommonGoodDto;
import ru.chsergeig.shoppy.dto.admin.GoodDto;
import ru.chsergeig.shoppy.jooq.enums.AccountRole;
import ru.chsergeig.shoppy.properties.SecurityProperties;
import ru.chsergeig.shoppy.service.UserService;
import ru.chsergeig.shoppy.service.common.CommonGoodService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RequestMapping("/goods")
@RestController
@CrossOrigin
public class CommonGoodController {

    private final CommonGoodService commonGoodService;
    private final SecurityProperties securityProperties;
    private final TokenUtilComponent tokenUtilComponent;
    private final UserService userService;

    @Operation(
            summary = "Get paginated goods according filter query and pagination",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All good")
            }
    )
    @GetMapping("get_all")
    public ResponseEntity<Page<CommonGoodDto>> getAllGoodsUsingFilterAndPagination(
            @RequestParam(value = "filter", required = false) String filter,
            Pageable pageable
    ) {
        return ResponseEntity.ok(commonGoodService.getAllGoodsUsingFilterAndPagination(filter, pageable));
    }

    @PostMapping("get_by_id")
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<List<GoodDto>> getGoodsByIds(
            HttpServletRequest httpServletRequest,
            @RequestBody Set<Integer> ids
    ) {
        final String token = httpServletRequest.getHeader(securityProperties.getJwt().getAuthorizationHeader());
        final String login = tokenUtilComponent.getUsernameFromToken(token);
        List<AccountRole> roles = userService.getUserRoles(login);
        if (roles.contains(AccountRole.ROLE_USER) || roles.contains(AccountRole.ROLE_ADMIN)) {
            return ResponseEntity.ok(commonGoodService.getGoodsByIds(ids));
        } else {
            return ResponseEntity.ok(List.of());
        }
    }

}
