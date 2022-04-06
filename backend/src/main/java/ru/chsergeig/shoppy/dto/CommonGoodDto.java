package ru.chsergeig.shoppy.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.chsergeig.shoppy.jooq.enums.Status;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CommonGoodDto {

    public String name;
    public String article;
    public Status status;
    public BigDecimal price;

    public CommonGoodDto(CommonGoodDto dto) {
        this.name = dto.name;
        this.article = dto.article;
        this.status = dto.status;
        this.price = dto.price;
    }

}
