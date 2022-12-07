package ru.chsergeig.shoppy.controller

import lombok.RequiredArgsConstructor
import org.jooq.tools.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.InsufficientAuthenticationException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.chsergeig.shoppy.api.JwtApiController
import ru.chsergeig.shoppy.model.JwtRequestDto
import ru.chsergeig.shoppy.model.JwtResponseDto
import ru.chsergeig.shoppy.properties.SecurityProperties
import ru.chsergeig.shoppy.provider.HttpServletRequestProvider
import ru.chsergeig.shoppy.service.AuthenticationService

@RequiredArgsConstructor
@RestController
open class JwtAuthenticationController @Autowired constructor(
    private val authenticationService: AuthenticationService,
    private val passwordEncoder: PasswordEncoder,
    private val httpServletRequestProvider: HttpServletRequestProvider,
    private val securityProperties: SecurityProperties

) : JwtApiController() {

    override fun doLogin(
        @RequestBody jwtRequestDto: JwtRequestDto?
    ): ResponseEntity<JwtResponseDto> {
        return try {
            ResponseEntity.ok(
                authenticationService.authenticate(
                    jwtRequestDto?.login!!,
                    jwtRequestDto.password!!
                )
            )
        } catch (ae: AuthenticationException) {
            if (StringUtils.isBlank(ae.localizedMessage)) {
                throw InsufficientAuthenticationException("Cant authenticate user", ae)
            }
            throw ae
        } catch (e: Exception) {
            throw InsufficientAuthenticationException("Cant authenticate user", e)
        }
    }

    override fun probeToken(): ResponseEntity<String> {
        val token = httpServletRequestProvider
            .getHttpServletRequest()
            .getHeader(securityProperties.jwt.authorizationHeader)
        return try {
            ResponseEntity.ok(authenticationService.probeToken(token).toString())
        } catch (ignore: Exception) {
            ResponseEntity.ok("false")
        }
    }

    override fun logout(): ResponseEntity<String> {
        val token = httpServletRequestProvider
            .getHttpServletRequest()
            .getHeader(securityProperties.jwt.authorizationHeader)
        authenticationService.logout(token)
        return ResponseEntity.ok("Logged out")
    }

    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthenticationException(e: AuthenticationException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.localizedMessage)
    }
}
