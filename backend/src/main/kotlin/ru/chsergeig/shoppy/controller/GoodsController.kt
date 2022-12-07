package ru.chsergeig.shoppy.controller

import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.chsergeig.shoppy.api.GoodsApiController
import ru.chsergeig.shoppy.jooq.enums.AccountRole
import ru.chsergeig.shoppy.model.CommonGoodDto
import ru.chsergeig.shoppy.properties.SecurityProperties
import ru.chsergeig.shoppy.provider.HttpServletRequestProvider
import ru.chsergeig.shoppy.service.UserService
import ru.chsergeig.shoppy.service.common.CommonGoodService
import ru.chsergeig.shoppy.utils.TokenUtils.Companion.getUsernameFromToken
import java.util.stream.Collectors

@RequiredArgsConstructor
@RestController
class GoodsController @Autowired constructor(
    private val commonGoodService: CommonGoodService,
    private val httpServletRequestProvider: HttpServletRequestProvider,
    private val securityProperties: SecurityProperties,
    private val userService: UserService
) : GoodsApiController() {

    override fun getAllGoodsUsingFilterAndPagination(
        @RequestParam("filter") filter: String?,
        @RequestParam("page") page: Int?,
        @RequestParam("size") size: Int?
    ): ResponseEntity<List<CommonGoodDto>> {
        return ResponseEntity.ok(
            commonGoodService.getAllGoodsUsingFilterAndPagination(
                filter,
                Pageable
                    .ofSize(size ?: 25)
                    .withPage(page ?: 0)
            ).content
        )
    }

    override fun getGoodsByIds(
        @RequestBody requestBody: List<String>?
    ): ResponseEntity<List<CommonGoodDto>> {
        val token = httpServletRequestProvider
            .getHttpServletRequest()
            .getHeader(securityProperties.jwt.authorizationHeader)
        val login = getUsernameFromToken(securityProperties, token)
        val roles = userService.getUserRoles(login!!)
        return if (roles.contains(AccountRole.ROLE_USER) || roles.contains(AccountRole.ROLE_ADMIN)) {
            ResponseEntity.ok(
                commonGoodService.getGoodsByIds(
                    requestBody!!.stream().map { s: String -> s.toInt() }.collect(Collectors.toList())
                )
            )
        } else {
            ResponseEntity.ok(listOf())
        }
    }
}
