package ru.chsergeig.shoppy.controller.common

import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.boot.test.mock.mockito.MockBean
import ru.chsergeig.shoppy.AbstractApiTest
import ru.chsergeig.shoppy.service.common.CommonOrdersService

internal class CommonOrderControllerTest : AbstractApiTest() {

    @MockBean
    lateinit var commonOrdersService: CommonOrdersService

    override fun flushMocks() {
        Mockito.reset(commonOrdersService)
    }

    @Test
    fun createOrderTest() {
        login()
    }
}
