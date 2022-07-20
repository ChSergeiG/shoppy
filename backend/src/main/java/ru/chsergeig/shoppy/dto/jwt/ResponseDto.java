package ru.chsergeig.shoppy.dto.jwt;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseDto {

    private final String token;
    private final LocalDateTime expirationTime;

}
