package ru.chsergeig.shoppy.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.chsergeig.shoppy.jooq.enums.Status;

@Data
@NoArgsConstructor
public class CommonOrderDto {

    private Integer id;
    private String info;
    private Status status;

    public CommonOrderDto(CommonOrderDto otherDto) {
        this.id = otherDto.id;
        this.info = otherDto.info;
        this.status = otherDto.status;
    }

}
