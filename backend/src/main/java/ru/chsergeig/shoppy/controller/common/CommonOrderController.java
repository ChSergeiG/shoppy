package ru.chsergeig.shoppy.controller.common;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.chsergeig.shoppy.component.TokenUtilComponent;
import ru.chsergeig.shoppy.dto.admin.GoodDto;
import ru.chsergeig.shoppy.dto.admin.OrderDto;
import ru.chsergeig.shoppy.exception.ControllerException;
import ru.chsergeig.shoppy.properties.SecurityProperties;
import ru.chsergeig.shoppy.service.common.CommonOrdersService;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/orders")
@RestController
@CrossOrigin
public class CommonOrderController {

    private final CommonOrdersService commonOrdersService;
    private final SecurityProperties securityProperties;
    private final TokenUtilComponent tokenUtilComponent;

    @Operation(
            summary = "Place order",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Order created, returns path to order view"),
                    @ApiResponse(responseCode = "401", description = "Credentials error"),
                    @ApiResponse(responseCode = "499", description = "Request error"),
            }
    )
    @PostMapping("create")
    public ResponseEntity<String> getAllGoodsUsingFilterAndPagination(
            HttpServletRequest httpServletRequest,
            @RequestBody List<GoodDto> goods
    ) {
        String token = httpServletRequest.getHeader(securityProperties.getJwt().getAuthorizationHeader());
        verifyTokenValidity(token);
        try {
            String guid = commonOrdersService.createOrder(goods, tokenUtilComponent.getUsernameFromToken(token));
            return ResponseEntity.created(URI.create("/orders/get/" + guid)).body("Successfully created");
        } catch (Exception e) {
            throw new ControllerException(
                    499,
                    "Cant place order",
                    e
            );
        }
    }

    @Operation(
            summary = "Get order info",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All good"),
                    @ApiResponse(responseCode = "401", description = "Credentials error"),
                    @ApiResponse(responseCode = "499", description = "Request error"),
            }
    )
    @GetMapping("get/{guid}")
    public ResponseEntity<OrderDto> getOrderInfo(
            HttpServletRequest httpServletRequest,
            @PathVariable("guid") String guid
    ) {
        String token = httpServletRequest.getHeader(securityProperties.getJwt().getAuthorizationHeader());
        verifyTokenValidity(token);
        try {
            OrderDto order = commonOrdersService.getOrderByGuid(guid, tokenUtilComponent.getUsernameFromToken(token));
            if (order == null) {
                throw new ControllerException(
                        401,
                        "You are not authorized to inspect this good",
                        null
                );
            }
            return ResponseEntity.ok(order);
        } catch (ControllerException ce) {
            throw ce;
        } catch (Exception e) {
            throw new ControllerException(
                    400,
                    "Cant get good info",
                    e
            );
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    void verifyTokenValidity(final String token) {
        if (token == null || token.length() == 0 || tokenUtilComponent.isTokenExpired(token)) {
            throw new ControllerException(
                    401,
                    "You are not authorized",
                    null
            );
        }
    }
}
