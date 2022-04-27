package ru.chsergeig.shoppy.controller.common

import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import ru.chsergeig.shoppy.AbstractApiTest
import ru.chsergeig.shoppy.component.TokenUtilComponent
import ru.chsergeig.shoppy.jooq.enums.AccountRole.ROLE_ADMIN
import ru.chsergeig.shoppy.model.JwtUserDetails
import ru.chsergeig.shoppy.properties.SecurityProperties
import ru.chsergeig.shoppy.service.common.CommonOrdersService
import java.util.UUID
import kotlin.random.Random

open class CommonOrderControllerTest : AbstractApiTest() {

    @MockBean
    lateinit var commonOrdersService: CommonOrdersService

    @Autowired
    lateinit var securityProperties: SecurityProperties

    @Autowired
    lateinit var tokenUtilComponent: TokenUtilComponent

    override fun flushMocks() {
        Mockito.reset(commonOrdersService)
    }

    @Test
    open fun createOrderTest() {
        login("")
    }


}