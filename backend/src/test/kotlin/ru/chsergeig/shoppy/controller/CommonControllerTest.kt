package ru.chsergeig.shoppy.controller

import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.hamcrest.Matchers.hasItem
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import ru.chsergeig.shoppy.AbstractApiTest
import ru.chsergeig.shoppy.jooq.enums.AccountRole
import ru.chsergeig.shoppy.jooq.enums.Status

open class CommonControllerTest : AbstractApiTest() {

    override fun flushMocks() {
    }

    @Test
    open fun getStatuses() {
        var validatableResponse = RestAssured.given()
            .spec(spec)
            .`when`()
            .get("/common/enum/statuses")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(ContentType.JSON)
        Status.values().map {
            validatableResponse = validatableResponse.body("$", hasItem(it.name))
        }
    }

    @Test
    open fun getUserRoles() {
        var validatableResponse = RestAssured.given()
            .spec(spec)
            .`when`()
            .get("/common/enum/account-roles")
            .then()
            .statusCode(HttpStatus.OK.value())
            .contentType(ContentType.JSON)
        AccountRole.values().map {
            validatableResponse = validatableResponse.body("$", hasItem(it.name))
        }
    }
}