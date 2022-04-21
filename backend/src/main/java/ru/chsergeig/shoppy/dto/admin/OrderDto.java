package ru.chsergeig.shoppy.dto.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;
import ru.chsergeig.shoppy.dto.CommonOrderDto;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class OrderDto extends CommonOrderDto {

    private String guid;

    public OrderDto(@Nullable CommonOrderDto otherDto) {
        if (otherDto != null) {
            setId(otherDto.getId());
            setInfo(otherDto.getInfo());
            setStatus(otherDto.getStatus());
        }
    }

}
