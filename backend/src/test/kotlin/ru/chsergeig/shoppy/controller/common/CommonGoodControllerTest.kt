package ru.chsergeig.shoppy.controller.common

import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyCollection
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito
import org.mockito.Mockito.doReturn
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import ru.chsergeig.shoppy.AbstractApiTest
import ru.chsergeig.shoppy.dao.GoodRepository
import ru.chsergeig.shoppy.jooq.enums.AccountRole.ROLE_ADMIN
import ru.chsergeig.shoppy.jooq.tables.pojos.Goods
import ru.chsergeig.shoppy.model.CommonGoodDto
import ru.chsergeig.shoppy.model.JwtUserDetails
import ru.chsergeig.shoppy.properties.SecurityProperties
import ru.chsergeig.shoppy.service.UserService
import ru.chsergeig.shoppy.service.common.CommonGoodService
import ru.chsergeig.shoppy.utils.TokenUtils
import java.util.UUID
import kotlin.random.Random
import org.mockito.Mockito.`when` as mockitoWhen

internal class CommonGoodControllerTest : AbstractApiTest() {

    @MockBean
    lateinit var commonGoodService: CommonGoodService

    @MockBean
    lateinit var goodRepository: GoodRepository

    @Autowired
    lateinit var securityProperties: SecurityProperties

    @MockBean
    lateinit var userService: UserService

    override fun flushMocks() {
        Mockito.reset(commonGoodService)
        Mockito.reset(goodRepository)
        Mockito.reset(userService)
    }

    @Test
    @Disabled
    fun getAllGoodsUsingFilterAndPagination() {
        val filter = UUID.randomUUID().toString()
        val size = Random.nextInt(10, 20)
        val page = Random.nextInt(10, 20)

        val pageable = Pageable.ofSize(size).withPage(page)

        doReturn(
            listOf(
                Goods(),
                Goods()
            )
        )
            .`when`(goodRepository).fetchByFilterAndPagination(eq(filter), eq(pageable))

        doReturn(322)
            .`when`(goodRepository).countActive()

        doReturn(
            PageImpl(
                listOf(CommonGoodDto(), CommonGoodDto()),
                pageable,
                322
            )
        )
        mockitoWhen(
            commonGoodService.getAllGoodsUsingFilterAndPagination(eq(filter), eq(pageable))
        )
            .thenReturn(
                PageImpl(
                    listOf(CommonGoodDto(), CommonGoodDto()),
                    pageable,
                    322
                )
            )

        given()
            .spec(spec)
            .`when`()
            .get("/goods/get_all?filter=$filter&size=$size&page=$page")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(ContentType.JSON)
            .body("totalElements", equalTo(400))
            .body("size", equalTo(2))
            .body("number", equalTo(1))
            .body("content.size()", equalTo(2))
    }

    @Test
    fun getGoodsByIds() {
        val userDetails = JwtUserDetails(
            Random.nextInt().toLong(),
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            listOf(ROLE_ADMIN.name)
        )

        val token = TokenUtils.generateToken(securityProperties, userDetails)

        mockitoWhen(userService.getUserRoles(userDetails.username))
            .thenReturn(listOf(ROLE_ADMIN))

        mockitoWhen(commonGoodService.getGoodsByIds(anyCollection()))
            .thenReturn(listOf(CommonGoodDto(), CommonGoodDto()))

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

        mockitoWhen(userService.getUserRoles(userDetails.username))
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
