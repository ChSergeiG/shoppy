package ru.chsergeig.shoppy.dto.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.chsergeig.shoppy.dto.CommonOrderDto;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class OrderDto extends CommonOrderDto {
    private String guid;
}
