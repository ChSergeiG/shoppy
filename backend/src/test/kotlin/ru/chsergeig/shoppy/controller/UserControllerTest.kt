package ru.chsergeig.shoppy.controller

import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.hamcrest.Matchers.empty
import org.hamcrest.Matchers.hasItems
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test
import org.mockito.Mockito.reset
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import ru.chsergeig.shoppy.AbstractApiTest
import ru.chsergeig.shoppy.jooq.enums.AccountRole.ROLE_ADMIN
import ru.chsergeig.shoppy.jooq.enums.AccountRole.ROLE_USER
import ru.chsergeig.shoppy.model.JwtUserDetails
import ru.chsergeig.shoppy.properties.SecurityProperties
import ru.chsergeig.shoppy.service.UserService
import ru.chsergeig.shoppy.utils.TokenUtils
import java.util.UUID
import kotlin.random.Random
import org.mockito.Mockito.`when` as mockitoWhen

internal class UserControllerTest : AbstractApiTest() {

    @Autowired
    lateinit var securityProperties: SecurityProperties

    @MockBean
    lateinit var userService: UserService

    override fun flushMocks() {
        reset(userService)
    }

    @Test
    fun getUserRoles() {
        val userDetails = JwtUserDetails(
            Random.nextInt().toLong(),
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            listOf(ROLE_USER.name, ROLE_ADMIN.name)
        )

        val token = TokenUtils.generateToken(securityProperties, userDetails)

        mockitoWhen(userService.getUserRoles(userDetails.username))
            .thenReturn(listOf(ROLE_USER, ROLE_ADMIN))

        RestAssured.given()
            .spec(spec)
            .`when`()
            .header(securityProperties.jwt.authorizationHeader, token)
            .get("/user/roles")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(ContentType.JSON)
            .body("$", hasItems("ROLE_USER", "ROLE_ADMIN"))

        RestAssured.given()
            .spec(spec)
            .`when`()
            .header(securityProperties.jwt.authorizationHeader, "")
            .get("/user/roles")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(ContentType.JSON)
            .body("$", `is`(empty<String>()))
    }
}
