package ru.chsergeig.shoppy.controller.common;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.chsergeig.shoppy.dto.CommonGoodDto;
import ru.chsergeig.shoppy.service.common.CommonGoodService;

@RequiredArgsConstructor
@RequestMapping("/goods")
@RestController
@CrossOrigin
public class CommonGoodController {

    private final CommonGoodService commonGoodService;

    @Operation(
            summary = "Get paginated goods according filter query and pagination",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All good")
            }
    )
    @GetMapping("get_all")
    public ResponseEntity<Page<CommonGoodDto>> getAllGoodsUsingFilterAndPagination(
            @RequestParam(value = "filter", required = false) String filter,
            Pageable pageable
    ) {
        return ResponseEntity.ok(commonGoodService.getAllGoodsUsingFilterAndPagination(filter, pageable));
    }


}
