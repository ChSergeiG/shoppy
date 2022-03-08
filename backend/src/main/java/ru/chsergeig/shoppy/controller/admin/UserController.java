package ru.chsergeig.shoppy.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.chsergeig.shoppy.dto.UserDTO;
import ru.chsergeig.shoppy.service.UserService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/admin/user")
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("get_all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        try {
            return ResponseEntity.ok(userService.getAllUsers());
        } catch (Exception e) {
            throw new ResponseStatusException(
                    499,
                    "Cant get users list: " + e.getLocalizedMessage(),
                    e
            );
        }
    }

    @PutMapping("{name}")
    public ResponseEntity<UserDTO> addDefaultUser(
            @PathVariable String name
    ) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(name));
        } catch (Exception e) {
            throw new ResponseStatusException(
                    499,
                    "Cant create new user: " + e.getLocalizedMessage(),
                    e
            );
        }
    }

    @PostMapping("add")
    public ResponseEntity<UserDTO> addUserPost(
            @RequestBody UserDTO dto
    ) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(dto));
        } catch (Exception e) {
            throw new ResponseStatusException(
                    499,
                    "Cant create new user: " + e.getLocalizedMessage(),
                    e
            );
        }
    }

    @PostMapping("update")
    public ResponseEntity<UserDTO> updateUser(
            @RequestBody UserDTO dto
    ) {
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.updateUser(dto));
        } catch (Exception e) {
            throw new ResponseStatusException(
                    499,
                    "Cant update user: " + e.getLocalizedMessage(),
                    e
            );
        }
    }

    @DeleteMapping("{name}")
    public ResponseEntity<Integer> deleteUser(
            @PathVariable String name
    ) {
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.deleteUser(name));
        } catch (Exception e) {
            throw new ResponseStatusException(
                    499,
                    "Cant delete user: " + e.getLocalizedMessage(),
                    e
            );
        }
    }
}
