package ru.chsergeig.shoppy.controller.common

import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito
import org.mockito.Mockito.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import ru.chsergeig.shoppy.AbstractApiTest
import ru.chsergeig.shoppy.component.TokenUtilComponent
import ru.chsergeig.shoppy.dto.CommonGoodDto
import ru.chsergeig.shoppy.dto.admin.GoodDto
import ru.chsergeig.shoppy.jooq.enums.AccountRole.ROLE_ADMIN
import ru.chsergeig.shoppy.model.JwtUserDetails
import ru.chsergeig.shoppy.properties.SecurityProperties
import ru.chsergeig.shoppy.service.UserService
import ru.chsergeig.shoppy.service.common.CommonGoodService
import java.util.UUID
import java.util.stream.IntStream
import kotlin.random.Random
import org.mockito.Mockito.`when` as mockitoWhen


open class CommonGoodControllerTest : AbstractApiTest() {

    @MockBean
    lateinit var commonGoodService: CommonGoodService

    @Autowired
    lateinit var securityProperties: SecurityProperties

    @Autowired
    lateinit var tokenUtilComponent: TokenUtilComponent

    @MockBean
    lateinit var userService: UserService

    override fun flushMocks() {
        Mockito.reset(commonGoodService)
        Mockito.reset(userService)
    }

    @Test
    open fun getAllGoodsUsingFilterAndPagination() {
        val filter = UUID.randomUUID().toString()
        val size = Random.nextInt(10, 20)
        val page = Random.nextInt(10, 20)

        val dtos = mutableListOf<CommonGoodDto>()
        IntStream.range(0, 400).forEach { dtos.add(it, CommonGoodDto()) }

        val response = PageImpl(
            dtos.subList(size * (page - 1), size * page),
            Pageable.ofSize(size).withPage(page),
            400
        )

        mockitoWhen(commonGoodService.getAllGoodsUsingFilterAndPagination(eq(filter), any()))
            .thenReturn(response)

        given()
            .spec(spec)
            .`when`()
            .get("/goods/get_all?filter=${filter}&size=${size}&page=${page}")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(ContentType.JSON)
            .body("totalElements", equalTo(400))
            .body("size", equalTo(size))
            .body("number", equalTo(page))
            .body("content.size()", equalTo(size))
    }

    @Test
    open fun getGoodsByIds() {
        val userDetails = JwtUserDetails(
            Random.nextInt().toLong(),
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            listOf()
        )

        val token = tokenUtilComponent.generateToken(userDetails)

        mockitoWhen(userService.getUserRoles(eq(userDetails.username)))
            .thenReturn(listOf(ROLE_ADMIN))

        mockitoWhen(commonGoodService.getGoodsByIds(any()))
            .thenReturn(listOf(GoodDto(), GoodDto()))

        given()
            .spec(spec)
            .`when`()
            .header(securityProperties.jwt.authorizationHeader, token)
            .body("[1,5,100]")
            .post("/goods/get_by_id")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(ContentType.JSON)
            .body("size()", `is`(2))

        flushMocks()

        mockitoWhen(userService.getUserRoles(eq(userDetails.username)))
            .thenReturn(listOf())

        given()
            .spec(spec)
            .`when`()
            .header(securityProperties.jwt.authorizationHeader, token)
            .body("[1,5,100]")
            .post("/goods/get_by_id")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(ContentType.JSON)
            .body("size()", `is`(0))
    }

}