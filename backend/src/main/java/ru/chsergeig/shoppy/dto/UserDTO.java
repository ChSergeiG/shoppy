package ru.chsergeig.shoppy.dto;

import lombok.Data;
import ru.chsergeig.shoppy.jooq.enums.Status;

@Data
public class UserDTO {

    public Integer id;
    public String name;
    public String password;
    public Status status;

}
