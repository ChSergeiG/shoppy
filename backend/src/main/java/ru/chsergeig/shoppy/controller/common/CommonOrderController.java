package ru.chsergeig.shoppy.controller.common;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.chsergeig.shoppy.component.TokenUtilComponent;
import ru.chsergeig.shoppy.dto.ExtendedOrderDto;
import ru.chsergeig.shoppy.dto.admin.GoodDto;
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
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<String> getAllGoodsUsingFilterAndPagination(
            HttpServletRequest httpServletRequest,
            @RequestBody List<GoodDto> goods
    ) {
        String token = httpServletRequest.getHeader(securityProperties.getJwt().getAuthorizationHeader());
        try {
            final String login = tokenUtilComponent.validateTokenAndGetUsername(token);

            String guid = commonOrdersService.createOrder(goods, login);
            return ResponseEntity.created(URI.create("/orders/get/" + guid)).body("Successfully created");
        } catch (ControllerException ce) {
            throw ce;
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
    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    public ResponseEntity<ExtendedOrderDto> getOrderInfo(
            HttpServletRequest httpServletRequest,
            @PathVariable("guid") String guid
    ) {
        String token = httpServletRequest.getHeader(securityProperties.getJwt().getAuthorizationHeader());

        try {
            final String login = tokenUtilComponent.validateTokenAndGetUsername(token);
            return ResponseEntity.ok(commonOrdersService.getOrderByGuid(guid, login));
        } catch (ControllerException ce) {
            throw ce;
        } catch (Exception e) {
            throw new ControllerException(
                    HttpStatus.BAD_REQUEST,
                    "Cant get good info",
                    e
            );
        }
    }

}
