package ru.chsergeig.shoppy.dto.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CountedGoodDto extends GoodDto {

    private Integer count;

    public CountedGoodDto(GoodDto goodDto, Integer count) {
        super(goodDto, goodDto.getId());
        this.count = count;
    }

}
