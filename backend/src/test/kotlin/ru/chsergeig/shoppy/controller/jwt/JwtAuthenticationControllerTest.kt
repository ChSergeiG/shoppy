package ru.chsergeig.shoppy.controller.jwt

import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.mock
import org.mockito.Mockito.reset
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import ru.chsergeig.shoppy.AbstractApiTest
import ru.chsergeig.shoppy.component.TokenUtilComponent
import ru.chsergeig.shoppy.dto.jwt.RequestDto
import ru.chsergeig.shoppy.dto.jwt.ResponseDto
import ru.chsergeig.shoppy.exception.ControllerException
import ru.chsergeig.shoppy.model.JwtUserDetails
import ru.chsergeig.shoppy.properties.SecurityProperties
import java.time.LocalDateTime
import java.util.UUID
import kotlin.random.Random
import org.mockito.Mockito.`when` as mockitoWhen

class JwtAuthenticationControllerTest : AbstractApiTest() {

    @MockBean
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var securityProperties: SecurityProperties

    @MockBean
    lateinit var tokenUtilComponent: TokenUtilComponent

    @MockBean
    lateinit var userDetailsService: UserDetailsService

    override fun flushMocks() {
        reset(authenticationManager)
        reset(userDetailsService)
    }

    @Test
    fun obtainToken() {
        val userDetails = JwtUserDetails(
            Random.nextInt().toLong(),
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            listOf()
        )

        val authenticationToken = mock(Authentication::class.java)
        val tokenValue = UUID.randomUUID().toString()

        val requestDto = RequestDto()
        requestDto.login = userDetails.username
        requestDto.password = userDetails.password

        val responseDto = ResponseDto(
            tokenValue,
            LocalDateTime.now()
        )

        mockitoWhen(authenticationManager.authenticate(any()))
            .thenReturn(authenticationToken)
        mockitoWhen(userDetailsService.loadUserByUsername(eq(requestDto.login)))
            .thenReturn(userDetails)
        mockitoWhen(tokenUtilComponent.generateToken(any()))
            .thenReturn(tokenValue)

        given()
            .spec(spec)
            .`when`()
            .body(requestDto)
            .post("/jwt/login")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(ContentType.JSON)
            .body("token", equalTo(responseDto.token))

        flushMocks()

        mockitoWhen(authenticationManager.authenticate(any()))
            .thenReturn(authenticationToken)
        mockitoWhen(userDetailsService.loadUserByUsername(eq(requestDto.login)))
            .thenThrow(BadCredentialsException(""))

        given()
            .spec(spec)
            .`when`()
            .body(requestDto)
            .post("/jwt/login")
            .then()
            .statusCode(HttpStatus.UNAUTHORIZED.value())
            .contentType(ContentType.TEXT)
    }

    @Test
    fun probeToken() {
        val userDetails = JwtUserDetails(
            Random.nextInt().toLong(),
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            listOf()
        )

        val tokenValue = UUID.randomUUID().toString()

        mockitoWhen(tokenUtilComponent.getUsernameFromToken(eq(tokenValue)))
            .thenReturn(userDetails.username)
        mockitoWhen(userDetailsService.loadUserByUsername(eq(userDetails.username)))
            .thenReturn(userDetails)
        mockitoWhen(tokenUtilComponent.isTokenValid(eq(tokenValue), any()))
            .thenReturn(true)

        given()
            .spec(spec)
            .`when`()
            .header(securityProperties.jwt.authorizationHeader, tokenValue)
            .get("/jwt/probe")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(ContentType.TEXT)

        flushMocks()

        mockitoWhen(tokenUtilComponent.getUsernameFromToken(eq(tokenValue)))
            .thenReturn(userDetails.username)
        mockitoWhen(userDetailsService.loadUserByUsername(eq(userDetails.username)))
            .thenReturn(userDetails)
        mockitoWhen(tokenUtilComponent.isTokenValid(eq(tokenValue), any()))
            .thenReturn(false)

        given()
            .spec(spec)
            .`when`()
            .header(securityProperties.jwt.authorizationHeader, tokenValue)
            .get("/jwt/probe")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(ContentType.TEXT)

        flushMocks()

        mockitoWhen(tokenUtilComponent.getUsernameFromToken(eq(tokenValue)))
            .thenThrow(RuntimeException(""))

        given()
            .spec(spec)
            .`when`()
            .header(securityProperties.jwt.authorizationHeader, tokenValue)
            .get("/jwt/probe")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(ContentType.TEXT)

        flushMocks()

        mockitoWhen(tokenUtilComponent.getUsernameFromToken(eq(tokenValue)))
            .thenReturn(userDetails.username)
        mockitoWhen(userDetailsService.loadUserByUsername(eq(userDetails.username)))
            .thenThrow(UsernameNotFoundException(""))

        given()
            .spec(spec)
            .`when`()
            .header(securityProperties.jwt.authorizationHeader, tokenValue)
            .get("/jwt/probe")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(ContentType.TEXT)

        flushMocks()

        mockitoWhen(tokenUtilComponent.getUsernameFromToken(eq(tokenValue)))
            .thenReturn(userDetails.username)
        mockitoWhen(userDetailsService.loadUserByUsername(eq(userDetails.username)))
            .thenReturn(userDetails)
        mockitoWhen(tokenUtilComponent.isTokenValid(eq(tokenValue), any()))
            .thenThrow(ControllerException(HttpStatus.UNAUTHORIZED, "", null))

        given()
            .spec(spec)
            .`when`()
            .header(securityProperties.jwt.authorizationHeader, tokenValue)
            .get("/jwt/probe")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(ContentType.TEXT)
    }
}
