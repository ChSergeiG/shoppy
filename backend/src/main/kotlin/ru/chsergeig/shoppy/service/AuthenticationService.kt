package ru.chsergeig.shoppy.service

import ru.chsergeig.shoppy.dto.jwt.ResponseDto

interface AuthenticationService {

    /**
     * Process request to issue jwt token
     */
    fun authenticate(
        login: String?,
        password: String?
    ): ResponseDto

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
