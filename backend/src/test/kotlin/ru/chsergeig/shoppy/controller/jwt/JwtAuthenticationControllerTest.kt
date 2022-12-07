package ru.chsergeig.shoppy.controller.jwt

import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.Matchers.notNullValue
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.doNothing
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
import org.springframework.security.crypto.password.PasswordEncoder
import ru.chsergeig.shoppy.AbstractApiTest
import ru.chsergeig.shoppy.dao.JwtTokenRepository
import ru.chsergeig.shoppy.jooq.enums.Status
import ru.chsergeig.shoppy.model.JwtRequestDto
import ru.chsergeig.shoppy.model.JwtUserDetails
import ru.chsergeig.shoppy.properties.SecurityProperties
import ru.chsergeig.shoppy.utils.TokenUtils
import java.util.UUID
import kotlin.random.Random
import org.mockito.Mockito.`when` as mockitoWhen

internal class JwtAuthenticationControllerTest : AbstractApiTest() {

    @MockBean
    lateinit var authenticationManager: AuthenticationManager

    @MockBean
    lateinit var jwtTokenRepository: JwtTokenRepository

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    lateinit var securityProperties: SecurityProperties

    @MockBean
    lateinit var userDetailsService: UserDetailsService

    override fun flushMocks() {
        reset(authenticationManager)
        reset(jwtTokenRepository)
        reset(userDetailsService)
    }

    @Test
    fun obtainToken() {
        val password = UUID.randomUUID().toString()

        val userDetails = JwtUserDetails(
            Random.nextInt().toLong(),
            UUID.randomUUID().toString(),
            passwordEncoder.encode("${securityProperties.jwt.salt}$$$password"),
            listOf()
        )

        val authenticationToken = mock(Authentication::class.java)
        val tokenValue = TokenUtils.generateToken(securityProperties, userDetails)

        val requestDto = JwtRequestDto()
        requestDto.login = userDetails.username
        requestDto.password = password

        mockitoWhen(authenticationManager.authenticate(any()))
            .thenReturn(authenticationToken)
        mockitoWhen(userDetailsService.loadUserByUsername(eq(requestDto.login)))
            .thenReturn(userDetails)
        doNothing().`when`(jwtTokenRepository)
            .addToken(any(), any(), eq(Status.ACTIVE))
        given()
            .spec(spec)
            .`when`()
            .body(requestDto)
            .post("/jwt/login")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(ContentType.JSON)
            .body("token", notNullValue())

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

        val tokenValue = TokenUtils.generateToken(securityProperties, userDetails)

        mockitoWhen(userDetailsService.loadUserByUsername(eq(userDetails.username)))
            .thenReturn(userDetails)

        given()
            .spec(spec)
            .`when`()
            .header(securityProperties.jwt.authorizationHeader, tokenValue)
            .get("/jwt/probe")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(ContentType.TEXT)

        flushMocks()

        Thread.sleep(3_001)

        mockitoWhen(userDetailsService.loadUserByUsername(eq(userDetails.username)))
            .thenReturn(userDetails)

        given()
            .spec(spec)
            .`when`()
            .header(securityProperties.jwt.authorizationHeader, tokenValue)
            .get("/jwt/probe")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(ContentType.TEXT)

        flushMocks()

        given()
            .spec(spec)
            .`when`()
            .header(securityProperties.jwt.authorizationHeader, tokenValue)
            .get("/jwt/probe")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(ContentType.TEXT)

        flushMocks()

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

        mockitoWhen(userDetailsService.loadUserByUsername(eq(userDetails.username)))
            .thenReturn(userDetails)

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
