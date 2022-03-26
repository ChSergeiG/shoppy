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
import ru.chsergeig.shoppy.dto.admin.OrderDto;
import ru.chsergeig.shoppy.exception.ControllerException;
import ru.chsergeig.shoppy.service.admin.AdminOrderService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/admin/order")
@RestController
@Secured("ROLE_ADMIN")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    @Operation(
            summary = "Get all not removed orders",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All good"),
                    @ApiResponse(responseCode = "401", description = "Need authorization"),
                    @ApiResponse(responseCode = "499", description = "Error in backend"),
                    @ApiResponse(responseCode = "500", description = "Error in wrapper")
            }
    )
    @GetMapping("get_all")
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        try {
            return ResponseEntity.ok(adminOrderService.getAllOrders());
        } catch (Exception e) {
            throw new ControllerException(
                    499,
                    "Cant get orders list",
                    e
            );
        }
    }

    @Operation(
            summary = "Get good by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All good"),
                    @ApiResponse(responseCode = "401", description = "Need authorization"),
                    @ApiResponse(responseCode = "499", description = "Error in backend"),
                    @ApiResponse(responseCode = "500", description = "Error in wrapper")
            }
    )

    @GetMapping("{id}")
    public ResponseEntity<OrderDto> getGood(
            @PathVariable("id") Long id
    ) {
        try {
            return ResponseEntity.ok(adminOrderService.getOrderById(id));
        } catch (Exception e) {
            throw new ControllerException(
                    499,
                    "Cant get order by id",
                    e
            );
        }
    }

    @Operation(
            summary = "Create default order",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Order created"),
                    @ApiResponse(responseCode = "401", description = "Need authorization"),
                    @ApiResponse(responseCode = "499", description = "Cant create order"),
                    @ApiResponse(responseCode = "500", description = "Error in wrapper")
            }
    )
    @PutMapping("{info}")
    public ResponseEntity<OrderDto> addDefaultOrder(
            @PathVariable String info
    ) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(adminOrderService.addOrder(info));
        } catch (Exception e) {
            throw new ControllerException(
                    499,
                    "Cant create new order",
                    e
            );
        }
    }

    @Operation(
            summary = "Create order",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Order created"),
                    @ApiResponse(responseCode = "401", description = "Need authorization"),
                    @ApiResponse(responseCode = "499", description = "Cant create order"),
                    @ApiResponse(responseCode = "500", description = "Error in wrapper")
            }
    )
    @PostMapping("add")
    public ResponseEntity<OrderDto> addOrderPost(
            @RequestBody OrderDto dto
    ) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(adminOrderService.addOrder(dto));
        } catch (Exception e) {
            throw new ControllerException(
                    499,
                    "Cant create new order",
                    e
            );
        }
    }

    @Operation(
            summary = "Update existing order",
            responses = {
                    @ApiResponse(responseCode = "202", description = "Order updated"),
                    @ApiResponse(responseCode = "401", description = "Need authorization"),
                    @ApiResponse(responseCode = "499", description = "Cant update order"),
                    @ApiResponse(responseCode = "500", description = "Error in wrapper")
            }
    )
    @PostMapping("update")
    public ResponseEntity<OrderDto> updateOrder(
            @RequestBody OrderDto dto
    ) {
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(adminOrderService.updateOrder(dto));
        } catch (Exception e) {
            throw new ControllerException(
                    499,
                    "Cant update order",
                    e
            );
        }
    }

    @Operation(
            summary = "Delete existing order",
            responses = {
                    @ApiResponse(responseCode = "202", description = "Order deleted"),
                    @ApiResponse(responseCode = "401", description = "Need authorization"),
                    @ApiResponse(responseCode = "499", description = "Cant create order"),
                    @ApiResponse(responseCode = "500", description = "Error in wrapper")
            }
    )
    @DeleteMapping("{id}")
    public ResponseEntity<Integer> deleteOrder(
            @PathVariable Integer id
    ) {
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(adminOrderService.deleteOrder(id));
        } catch (Exception e) {
            throw new ControllerException(
                    499,
                    "Cant delete order",
                    e
            );
        }
    }

}
