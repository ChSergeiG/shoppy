package ru.chsergeig.shoppy.dto;

import lombok.Data;
import ru.chsergeig.shoppy.jooq.enums.Status;

@Data
public class OrderDTO {

    public Integer id;
    public String info;
    public Status status;

}
