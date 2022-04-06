package ru.chsergeig.shoppy.dto.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.chsergeig.shoppy.dto.CommonGoodDto;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class GoodDto extends CommonGoodDto {

    public Integer id;

}
