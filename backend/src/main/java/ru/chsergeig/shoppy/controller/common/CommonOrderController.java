package ru.chsergeig.shoppy.controller.common;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.chsergeig.shoppy.component.TokenUtilComponent;
import ru.chsergeig.shoppy.dto.admin.GoodDto;
import ru.chsergeig.shoppy.dto.admin.OrderDto;
import ru.chsergeig.shoppy.properties.SecurityProperties;
import ru.chsergeig.shoppy.service.common.CommonOrdersService;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/orders")
@RestController
@CrossOrigin
@Secured({"ROLE_ADMIN", "ROLE_USER"})
public class CommonOrderController {

    private final CommonOrdersService commonOrdersService;
    private final SecurityProperties securityProperties;
    private final TokenUtilComponent tokenUtilComponent;

    @Operation(
            summary = "Place order",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Order created, returns path to order view"),
                    @ApiResponse(responseCode = "401", description = "User not authorized")
            }
    )
    @PostMapping("create")
    public ResponseEntity<String> getAllGoodsUsingFilterAndPagination(
            HttpServletRequest httpServletRequest,
            @RequestBody List<GoodDto> goods
    ) {
        String token = httpServletRequest.getHeader(securityProperties.getJwt().getAuthorizationHeader());
        if (token == null || tokenUtilComponent.isTokenExpired(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authorized");
        }
        try {
            String guid = commonOrdersService.createOrder(goods, tokenUtilComponent.getUsernameFromToken(token));
            return ResponseEntity.created(URI.create("/orders/get/" + guid)).body("Successfully created");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request: " + e.getLocalizedMessage());
        }
    }

    @Operation(
            summary = "Get order info",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All good"),
                    @ApiResponse(responseCode = "401", description = "User not authorized")
            }
    )
    @PostMapping("get/{guid}")
    public ResponseEntity<OrderDto> getOrderInfo(
            HttpServletRequest httpServletRequest,
            @PathVariable("guid") String guid
    ) {


        return ResponseEntity.ok().build();
    }

}
