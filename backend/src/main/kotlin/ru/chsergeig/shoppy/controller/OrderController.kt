package ru.chsergeig.shoppy.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import ru.chsergeig.shoppy.api.OrdersApiController
import ru.chsergeig.shoppy.exception.ControllerException
import ru.chsergeig.shoppy.model.ExtendedOrderDto
import ru.chsergeig.shoppy.properties.SecurityProperties
import ru.chsergeig.shoppy.provider.HttpServletRequestProvider
import ru.chsergeig.shoppy.service.common.CommonOrdersService
import ru.chsergeig.shoppy.utils.TokenUtils.Companion.getUsernameFromToken
import java.net.URI

@RestController
open class OrderController @Autowired constructor(
    private val commonOrdersService: CommonOrdersService,
    private val httpServletRequestProvider: HttpServletRequestProvider,
    private val securityProperties: SecurityProperties
) : OrdersApiController() {

    override fun createOrder(): ResponseEntity<Unit> =
        try {
            val tokenValue: String = httpServletRequestProvider
                .getHttpServletRequest()
                .getHeader(securityProperties.jwt.authorizationHeader)
            val login = getUsernameFromToken(securityProperties, tokenValue)
            val guid = commonOrdersService.createOrder(listOf(), login)
            ResponseEntity.created(URI.create("/orders/get/$guid")).build()
        } catch (e: Exception) {
            throw ControllerException(
                499,
                "Cant create new order",
                e
            )
        }

    override fun getOrderInfoByGuid(
        @PathVariable guid: String
    ): ResponseEntity<ExtendedOrderDto> =
        try {
            val tokenValue: String = httpServletRequestProvider
                .getHttpServletRequest()
                .getHeader(securityProperties.jwt.authorizationHeader)
            val login = getUsernameFromToken(securityProperties, tokenValue)
            ResponseEntity.ok(commonOrdersService.getOrderByGuid(guid, login))
        } catch (e: Exception) {
            throw ControllerException(
                499,
                "Cant get order info by guid",
                e
            )
        }
}
