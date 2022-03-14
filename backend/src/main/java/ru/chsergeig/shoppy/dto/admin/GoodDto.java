package ru.chsergeig.shoppy.dto.admin;

import lombok.Data;
import ru.chsergeig.shoppy.jooq.enums.Status;

@Data
public class GoodDto {

    public Integer id;
    public String name;
    public Integer article;
    public Status status;

}
