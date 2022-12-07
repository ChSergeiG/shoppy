package ru.chsergeig.shoppy.controller

import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import ru.chsergeig.shoppy.api.CommonApiController
import ru.chsergeig.shoppy.model.AccountRole
import ru.chsergeig.shoppy.model.Status

@RequiredArgsConstructor
@RestController
class CommonController : CommonApiController() {

    override fun getAllRoles(): ResponseEntity<List<AccountRole>> = ResponseEntity.ok(listOf(*AccountRole.values()))

    override fun getAllStatuses(): ResponseEntity<List<Status>> = ResponseEntity.ok(listOf(*Status.values()))
}
