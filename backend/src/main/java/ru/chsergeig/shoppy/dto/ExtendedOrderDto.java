package ru.chsergeig.shoppy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;
import ru.chsergeig.shoppy.dto.admin.GoodDto;
import ru.chsergeig.shoppy.dto.admin.OrderDto;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ExtendedOrderDto extends OrderDto {

    private List<OrderEntry> goods;

    public ExtendedOrderDto(@Nullable CommonOrderDto otherDto) {
        if (otherDto != null) {
            setId(otherDto.getId());
            setInfo(otherDto.getInfo());
            setStatus(otherDto.getStatus());
        }
    }

    @Data
    @AllArgsConstructor
    public static class OrderEntry {
        private GoodDto good;
        private Long count;
    }

}
