package ru.chsergeig.shoppy.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "shoppy.security")
data class SecurityProperties(
    val jwt: Jwt,
    val user: User
) {

    data class Jwt(
        val authorizationHeader: String,
        val secret: String,
        val expirationInSeconds: Long,
        val salt: String
    )

    data class User(
        val login: String,
        val password: String
    )
}
