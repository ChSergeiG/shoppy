package ru.chsergeig.shoppy.service;

import ru.chsergeig.shoppy.dto.jwt.ResponseDto;

public interface AuthenticationService {

    /**
     * Process request to issue jwt token
     */
    ResponseDto authenticate(String login, String password);

}
