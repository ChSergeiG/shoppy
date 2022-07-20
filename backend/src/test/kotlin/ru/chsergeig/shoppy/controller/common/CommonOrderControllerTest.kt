package ru.chsergeig.shoppy.controller.common

import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import ru.chsergeig.shoppy.AbstractApiTest
import ru.chsergeig.shoppy.component.TokenUtilComponent
import ru.chsergeig.shoppy.properties.SecurityProperties
import ru.chsergeig.shoppy.service.common.CommonOrdersService

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
