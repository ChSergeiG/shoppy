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
import ru.chsergeig.shoppy.dto.admin.GoodDto;
import ru.chsergeig.shoppy.service.admin.GoodService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/admin/good")
@RestController
@Secured("ROLE_ADMIN")
@CrossOrigin
public class GoodController {

    private final GoodService goodsService;

    @GetMapping("get_all")
    public ResponseEntity<List<GoodDto>> getAllGoods() {
        try {
            return ResponseEntity.ok(goodsService.getAllGoods());
        } catch (Exception e) {
            throw new ResponseStatusException(
                    499,
                    "Cant get goods list: " + e.getLocalizedMessage(),
                    e
            );
        }
    }

    @PutMapping("{name}")
    public ResponseEntity<GoodDto> addDefaultGood(
            @PathVariable String name
    ) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(goodsService.addGood(name));
        } catch (Exception e) {
            throw new ResponseStatusException(
                    499,
                    "Cant create new good: " + e.getLocalizedMessage(),
                    e
            );
        }
    }

    @PostMapping("add")
    public ResponseEntity<GoodDto> addGoodPost(
            @RequestBody GoodDto dto
    ) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(goodsService.addGood(dto));
        } catch (Exception e) {
            throw new ResponseStatusException(
                    499,
                    "Cant create new good: " + e.getLocalizedMessage(),
                    e
            );
        }
    }

    @PostMapping("update")
    public ResponseEntity<GoodDto> updateGood(
            @RequestBody GoodDto dto
    ) {
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(goodsService.updateGood(dto));
        } catch (Exception e) {
            throw new ResponseStatusException(
                    499,
                    "Cant update good: " + e.getLocalizedMessage(),
                    e
            );
        }
    }

    @DeleteMapping("{article}")
    public ResponseEntity<Integer> deleteGood(
            @PathVariable Integer article
    ) {
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(goodsService.deleteGood(article));
        } catch (Exception e) {
            throw new ResponseStatusException(
                    499,
                    "Cant delete good: " + e.getLocalizedMessage(),
                    e
            );
        }
    }

}
