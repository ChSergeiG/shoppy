package ru.chsergeig.shoppy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.chsergeig.shoppy.jooq.enums.Status;

@RequiredArgsConstructor
@RequestMapping("/")
@RestController
public class CommonController {

    @GetMapping("statuses")
    public ResponseEntity<Status[]> getAll() {
        return ResponseEntity.ok(Status.values());
    }

}
