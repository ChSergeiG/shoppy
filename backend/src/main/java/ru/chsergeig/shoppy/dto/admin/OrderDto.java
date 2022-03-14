package ru.chsergeig.shoppy.dto.admin;

import lombok.Data;
import ru.chsergeig.shoppy.jooq.enums.Status;

@Data
public class OrderDto {

    public Integer id;
    public String info;
    public Status status;

}
