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
import ru.chsergeig.shoppy.dto.admin.GoodDto;
import ru.chsergeig.shoppy.exception.ControllerException;
import ru.chsergeig.shoppy.service.admin.AdminGoodService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/admin/good")
@RestController
@Secured("ROLE_ADMIN")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminGoodController {

    private final AdminGoodService adminGoodService;

    @Operation(
            summary = "Get all not removed goods",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All good"),
                    @ApiResponse(responseCode = "401", description = "Need authorization"),
                    @ApiResponse(responseCode = "499", description = "Error in backend"),
                    @ApiResponse(responseCode = "500", description = "Error in wrapper")
            }
    )
    @GetMapping("get_all")
    public ResponseEntity<List<GoodDto>> getAllGoods() {
        try {
            return ResponseEntity.ok(adminGoodService.getAllGoods());
        } catch (Exception e) {
            throw new ControllerException(
                    499,
                    "Cant get goods list",
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
    public ResponseEntity<GoodDto> getGood(
            @PathVariable("id") Long id
    ) {
        try {
            return ResponseEntity.ok(adminGoodService.getGoodById(id));
        } catch (Exception e) {
            throw new ControllerException(
                    499,
                    "Cant get good by id",
                    e
            );
        }
    }

    @Operation(
            summary = "Create default good",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Good created"),
                    @ApiResponse(responseCode = "401", description = "Need authorization"),
                    @ApiResponse(responseCode = "499", description = "Cant create good"),
                    @ApiResponse(responseCode = "500", description = "Error in wrapper")
            }
    )
    @PutMapping("{name}")
    public ResponseEntity<GoodDto> addDefaultGood(
            @PathVariable String name
    ) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(adminGoodService.addGood(name));
        } catch (Exception e) {
            throw new ControllerException(
                    499,
                    "Cant create new good",
                    e
            );
        }
    }

    @Operation(
            summary = "Create good",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Good created"),
                    @ApiResponse(responseCode = "401", description = "Need authorization"),
                    @ApiResponse(responseCode = "499", description = "Cant create good"),
                    @ApiResponse(responseCode = "500", description = "Error in wrapper")
            }
    )
    @PostMapping("add")
    public ResponseEntity<GoodDto> addGoodPost(
            @RequestBody GoodDto dto
    ) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(adminGoodService.addGood(dto));
        } catch (Exception e) {
            throw new ControllerException(
                    499,
                    "Cant create new good",
                    e
            );
        }
    }

    @Operation(
            summary = "Update existing good",
            responses = {
                    @ApiResponse(responseCode = "202", description = "Good updated"),
                    @ApiResponse(responseCode = "401", description = "Need authorization"),
                    @ApiResponse(responseCode = "499", description = "Cant update good"),
                    @ApiResponse(responseCode = "500", description = "Error in wrapper")
            }
    )
    @PostMapping("update")
    public ResponseEntity<GoodDto> updateGood(
            @RequestBody GoodDto dto
    ) {
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(adminGoodService.updateGood(dto));
        } catch (Exception e) {
            throw new ControllerException(
                    499,
                    "Cant update good",
                    e
            );
        }
    }

    @Operation(
            summary = "Delete existing good",
            responses = {
                    @ApiResponse(responseCode = "202", description = "Good deleted"),
                    @ApiResponse(responseCode = "401", description = "Need authorization"),
                    @ApiResponse(responseCode = "499", description = "Cant create good"),
                    @ApiResponse(responseCode = "500", description = "Error in wrapper")
            }
    )
    @DeleteMapping("{article}")
    public ResponseEntity<Integer> deleteGood(
            @PathVariable String article
    ) {
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(adminGoodService.deleteGood(article));
        } catch (Exception e) {
            throw new ControllerException(
                    499,
                    "Cant delete good",
                    e
            );
        }
    }

}
