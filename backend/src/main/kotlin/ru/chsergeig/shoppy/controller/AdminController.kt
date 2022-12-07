package ru.chsergeig.shoppy.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.chsergeig.shoppy.api.AdminApiController
import ru.chsergeig.shoppy.exception.ControllerException
import ru.chsergeig.shoppy.model.AdminAccountDto
import ru.chsergeig.shoppy.model.AdminCountedGoodDto
import ru.chsergeig.shoppy.model.AdminGoodDto
import ru.chsergeig.shoppy.model.AdminOrderAccountsDto
import ru.chsergeig.shoppy.model.AdminOrderDto
import ru.chsergeig.shoppy.model.AdminOrderGoodsDto
import ru.chsergeig.shoppy.properties.SecurityProperties
import ru.chsergeig.shoppy.provider.HttpServletRequestProvider
import ru.chsergeig.shoppy.service.admin.AdminAccountService
import ru.chsergeig.shoppy.service.admin.AdminGoodService
import ru.chsergeig.shoppy.service.admin.AdminOrderService
import ru.chsergeig.shoppy.utils.TokenUtils.Companion.validateTokenAndGetUsername

@RestController
@Secured("ROLE_ADMIN")
open class AdminController @Autowired constructor(
    private val adminAccountService: AdminAccountService,
    private val adminGoodService: AdminGoodService,
    private val adminOrderService: AdminOrderService,
    private val httpServletRequestProvider: HttpServletRequestProvider,
    private val securityProperties: SecurityProperties
) : AdminApiController() {

    override fun addDefaultAccountInAdminArea(
        @PathVariable login: String
    ): ResponseEntity<AdminAccountDto> =
        try {
            ResponseEntity.ok(adminAccountService.addAccount(login))
        } catch (e: Exception) {
            throw ControllerException(
                499,
                "Cant create default account",
                e
            )
        }

    override fun addDefaultOrderInAdminArea(
        @PathVariable id: String
    ): ResponseEntity<AdminOrderDto> =
        try {
            ResponseEntity.status(HttpStatus.CREATED).body(
                adminOrderService.addOrder(id)
            )
        } catch (e: Exception) {
            throw ControllerException(
                499,
                "Cant create new order",
                e
            )
        }

    override fun addGoodInAdminArea(
        @RequestBody adminGoodDto: AdminGoodDto?
    ): ResponseEntity<AdminGoodDto> =
        try {
            ResponseEntity.status(HttpStatus.CREATED).body(
                adminGoodService.addGood(adminGoodDto)
            )
        } catch (e: Exception) {
            throw ControllerException(
                499,
                "Cant create new good",
                e
            )
        }

    override fun addOrderInAdminArea(
        @RequestBody adminOrderDto: AdminOrderDto?
    ): ResponseEntity<AdminOrderDto> =
        try {
            ResponseEntity.status(HttpStatus.CREATED).body(
                adminOrderService.addOrder(adminOrderDto)
            )
        } catch (e: Exception) {
            throw ControllerException(
                499,
                "Cant create new order",
                e
            )
        }

    override fun addAccountInAdminArea(
        @RequestBody adminAccountDto: AdminAccountDto?
    ): ResponseEntity<AdminAccountDto> =
        try {
            ResponseEntity.status(HttpStatus.CREATED).body(
                adminAccountService.addAccount(adminAccountDto)
            )
        } catch (e: Exception) {
            throw ControllerException(
                499,
                "Cant create new account",
                e
            )
        }

    override fun deleteAccountInAdminAreaByLogin(
        @PathVariable login: String
    ): ResponseEntity<String> {
        if (isSelfModification(login)) {
            throw ControllerException(
                HttpStatus.NOT_MODIFIED,
                "Self modification is not allowed",
                null
            )
        }
        return try {
            ResponseEntity.status(HttpStatus.ACCEPTED).body(
                adminAccountService.deleteAccount(login).toString()
            )
        } catch (e: Exception) {
            throw ControllerException(
                499,
                "Cant delete account",
                e
            )
        }
    }

    override fun deleteGoodInAdminAreaByArticle(
        @PathVariable article: String
    ): ResponseEntity<String> {
        return try {
            ResponseEntity.status(HttpStatus.ACCEPTED).body(
                adminGoodService.deleteGood(article).toString()
            )
        } catch (e: Exception) {
            throw ControllerException(
                499,
                "Cant delete good",
                e
            )
        }
    }

    override fun deleteOrderInAdminAreaById(
        @PathVariable id: String
    ): ResponseEntity<String> =
        try {
            ResponseEntity.status(HttpStatus.ACCEPTED).body(
                adminOrderService.deleteOrder(id.toInt()).toString()
            )
        } catch (e: Exception) {
            throw ControllerException(
                499,
                "Cant delete order",
                e
            )
        }

    override fun getAccountInAdminAreaByLogin(
        @PathVariable login: String
    ): ResponseEntity<AdminAccountDto> =
        try {
            ResponseEntity.ok(
                adminAccountService.getAccountByLogin(login)
            )
        } catch (e: Exception) {
            throw ControllerException(
                499,
                "Cant get account by id",
                e
            )
        }

    override fun getAccountsInAdminAreaByOrderId(
        @PathVariable orderId: String
    ): ResponseEntity<List<AdminAccountDto>> =
        ResponseEntity.ok(
            adminOrderService.getAccountsByOrderId(orderId.toInt())
        )

    override fun getAccountsInAdminAreaByOrderIds(
        @RequestBody requestBody: List<Int>?
    ): ResponseEntity<List<AdminOrderAccountsDto>> = try {
        ResponseEntity.ok(
            adminOrderService.getAccountsByOrderIds(requestBody ?: listOf())
        )
    } catch (e: Exception) {
        throw ControllerException(
            499,
            "Cant get accounts list",
            e
        )
    }

    override fun getAllAccountsInAdminArea(): ResponseEntity<List<AdminAccountDto>> = try {
        ResponseEntity.ok(
            adminAccountService.getAllAccounts().filterNotNull()
        )
    } catch (e: Exception) {
        throw ControllerException(
            499,
            "Cant get accounts list",
            e
        )
    }

    override fun getAllGoodsInAdminArea(): ResponseEntity<List<AdminGoodDto>> =
        try {
            ResponseEntity.ok(
                adminGoodService.getAllGoods().requireNoNulls()
            )
        } catch (e: Exception) {
            throw ControllerException(
                499,
                "Cant get goods list",
                e
            )
        }

    override fun getAllOrdersInAdminArea(): ResponseEntity<List<AdminOrderDto>> =
        try {
            ResponseEntity.ok(
                adminOrderService.getAllOrders().requireNoNulls()
            )
        } catch (e: Exception) {
            throw ControllerException(
                499,
                "Cant get orders list",
                e
            )
        }

    override fun getGoodInAdminAreaById(
        @PathVariable id: String
    ): ResponseEntity<AdminGoodDto> =
        try {
            ResponseEntity.ok(
                adminGoodService.getGoodById(id.toLong())
            )
        } catch (e: Exception) {
            throw ControllerException(
                499,
                "Cant get good by id",
                e
            )
        }

    override fun getGoodsInAdminAreaByOrderId(
        @PathVariable orderId: String
    ): ResponseEntity<List<AdminCountedGoodDto>> =
        ResponseEntity.ok(
            adminOrderService.getGoodsByOrderId(orderId.toInt())
        )

    override fun getGoodsInAdminAreaByOrderIds(
        @RequestBody requestBody: List<Int>?
    ): ResponseEntity<List<AdminOrderGoodsDto>> = try {
        ResponseEntity.ok(
            adminOrderService.getGoodsByOrderIds(requestBody ?: listOf())
        )
    } catch (e: Exception) {
        throw ControllerException(
            499,
            "Cant get goods list",
            e
        )
    }

    override fun getOrderInAdminAreaById(
        @PathVariable id: String
    ): ResponseEntity<AdminOrderDto> =
        try {
            ResponseEntity.ok(
                adminOrderService.getOrderById(id.toLong())
            )
        } catch (e: Exception) {
            throw ControllerException(
                499,
                "Cant get order by id",
                e
            )
        }

    override fun putDefaultGoodInAdminArea(
        @PathVariable id: String
    ): ResponseEntity<AdminGoodDto> =
        try {
            ResponseEntity.status(HttpStatus.CREATED).body(
                adminGoodService.addGood(id)
            )
        } catch (e: Exception) {
            throw ControllerException(
                499,
                "Cant create new good",
                e
            )
        }

    override fun updateAccountInAdminArea(
        @RequestBody adminAccountDto: AdminAccountDto?
    ): ResponseEntity<AdminAccountDto> {
        return try {
            if (adminAccountDto?.login != null && isSelfModification(adminAccountDto.login)) {
                val (_, _, _, _, status, accountRoles) = adminAccountService.getAccountByLogin(adminAccountDto.login)
                adminAccountDto.accountRoles = accountRoles
                adminAccountDto.status = status
            }
            ResponseEntity.status(HttpStatus.ACCEPTED).body(
                adminAccountService.updateAccount(adminAccountDto)
            )
        } catch (e: Exception) {
            throw ControllerException(
                499,
                "Cant update account",
                e
            )
        }
    }

    override fun updateGoodInAdminArea(
        @RequestBody adminGoodDto: AdminGoodDto?
    ): ResponseEntity<AdminGoodDto> {
        return try {
            ResponseEntity.status(HttpStatus.ACCEPTED).body(
                adminGoodService.updateGood(adminGoodDto)
            )
        } catch (e: Exception) {
            throw ControllerException(
                499,
                "Cant update good",
                e
            )
        }
    }

    override fun updateOrderInAdminArea(
        @RequestBody adminOrderDto: AdminOrderDto?
    ): ResponseEntity<AdminOrderDto> {
        return try {
            ResponseEntity.status(HttpStatus.ACCEPTED).body(
                adminOrderService.updateOrder(adminOrderDto)
            )
        } catch (e: Exception) {
            throw ControllerException(
                499,
                "Cant update order",
                e
            )
        }
    }

    // //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private fun isSelfModification(login: String?): Boolean {
        val token = httpServletRequestProvider
            .getHttpServletRequest()
            .getHeader(securityProperties.jwt.authorizationHeader)
        return login == validateTokenAndGetUsername(securityProperties, token)
    }
}
