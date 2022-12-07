package ru.chsergeig.shoppy.controller

import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import ru.chsergeig.shoppy.api.UserApiController
import ru.chsergeig.shoppy.mapping.EnumMapper
import ru.chsergeig.shoppy.model.AccountRole
import ru.chsergeig.shoppy.properties.SecurityProperties
import ru.chsergeig.shoppy.provider.HttpServletRequestProvider
import ru.chsergeig.shoppy.service.UserService
import ru.chsergeig.shoppy.utils.TokenUtils.Companion.getUsernameFromToken

@RequiredArgsConstructor
@RestController
open class UserController @Autowired constructor(
    private val enumMapper: EnumMapper,
    private val httpServletRequestProvider: HttpServletRequestProvider,
    private val securityProperties: SecurityProperties,
    private val userService: UserService
) : UserApiController() {

    override fun getUserRoles(): ResponseEntity<List<AccountRole>> {
        val token = httpServletRequestProvider
            .getHttpServletRequest()
            .getHeader(securityProperties.jwt.authorizationHeader)
        if (!StringUtils.hasText(token)) {
            return ResponseEntity.ok(listOf())
        }
        val login = getUsernameFromToken(securityProperties, token)
        val roles = userService.getUserRoles(login!!)
        return ResponseEntity.ok(roles.map { enumMapper.fromJooq(it) })
    }

    @Secured("ROLE_USER", "ROLE_ADMIN")
    override fun getUserRolesByLogin(
        @PathVariable login: String
    ): ResponseEntity<List<AccountRole>> {
        val roles = userService.getUserRoles(login)
        return ResponseEntity.ok(roles.map { enumMapper.fromJooq(it) })
    }
}
