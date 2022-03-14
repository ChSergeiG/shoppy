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
import ru.chsergeig.shoppy.dto.admin.OrderDto;
import ru.chsergeig.shoppy.service.admin.OrderService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/admin/order")
@RestController
@Secured("ROLE_ADMIN")
@CrossOrigin
public class OrderController {

    private final OrderService orderService;

    @GetMapping("get_all")
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        try {
            return ResponseEntity.ok(orderService.getAllOrders());
        } catch (Exception e) {
            throw new ResponseStatusException(
                    499,
                    "Cant get orders list: " + e.getLocalizedMessage(),
                    e
            );
        }
    }

    @PutMapping("{info}")
    public ResponseEntity<OrderDto> addDefaultOrder(
            @PathVariable String info
    ) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(orderService.addOrder(info));
        } catch (Exception e) {
            throw new ResponseStatusException(
                    499,
                    "Cant create new order: " + e.getLocalizedMessage(),
                    e
            );
        }
    }

    @PostMapping("add")
    public ResponseEntity<OrderDto> addOrderPost(
            @RequestBody OrderDto dto
    ) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(orderService.addOrder(dto));
        } catch (Exception e) {
            throw new ResponseStatusException(
                    499,
                    "Cant create new order: " + e.getLocalizedMessage(),
                    e
            );
        }
    }

    @PostMapping("update")
    public ResponseEntity<OrderDto> updateOrder(
            @RequestBody OrderDto dto
    ) {
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(orderService.updateOrder(dto));
        } catch (Exception e) {
            throw new ResponseStatusException(
                    499,
                    "Cant update order: " + e.getLocalizedMessage(),
                    e
            );
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Integer> deleteOrder(
            @PathVariable Integer id
    ) {
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(orderService.deleteOrder(id));
        } catch (Exception e) {
            throw new ResponseStatusException(
                    499,
                    "Cant delete order: " + e.getLocalizedMessage(),
                    e
            );
        }
    }

}
