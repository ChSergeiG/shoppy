package ru.chsergeig.shoppy.dto;

import lombok.Data;
import ru.chsergeig.shoppy.jooq.enums.Status;

@Data
public class GoodDTO {

    public Integer id;
    public String name;
    public Integer article;
    public Status status;

}
