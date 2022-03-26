package ru.chsergeig.shoppy.dto.admin;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.chsergeig.shoppy.dto.CommonGoodDto;

@Data
@EqualsAndHashCode(callSuper = true)
public class GoodDto extends CommonGoodDto {

    public Integer id;

}
