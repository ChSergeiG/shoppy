package ru.chsergeig.shoppy.dto;

import lombok.Data;
import ru.chsergeig.shoppy.jooq.enums.Status;

@Data
public class CommonGoodDto {

    public String name;
    public String article;
    public Status status;

}
