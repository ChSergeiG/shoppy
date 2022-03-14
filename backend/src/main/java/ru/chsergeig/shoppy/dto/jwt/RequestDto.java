package ru.chsergeig.shoppy.dto.jwt;

import lombok.Data;

@Data
public class RequestDto {

    private String login;
    private String password;

}
