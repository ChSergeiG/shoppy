package ru.chsergeig.shoppy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(
            summary = "Get available status to assign entity",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All good")
            }
    )
    @GetMapping("statuses")
    public ResponseEntity<Status[]> getAll() {
        return ResponseEntity.ok(Status.values());
    }



}
