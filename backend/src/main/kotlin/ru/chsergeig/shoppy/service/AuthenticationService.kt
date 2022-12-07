package ru.chsergeig.shoppy.service

import ru.chsergeig.shoppy.model.JwtResponseDto

interface AuthenticationService {

    /**
     * Process request to issue jwt token
     */
    fun authenticate(
        login: String,
        password: String
    ): JwtResponseDto

    /**
     * Check is token healthy
     */
    fun probeToken(
        token: String?
    ): Boolean?

    /**
     * Do logout
     */
    fun logout(
        token: String?
    )
}
