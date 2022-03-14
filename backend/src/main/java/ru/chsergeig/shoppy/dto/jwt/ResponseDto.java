package ru.chsergeig.shoppy.dto.jwt;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ResponseDto {

    private final String token;
    private final LocalDateTime expirationTime;

}
