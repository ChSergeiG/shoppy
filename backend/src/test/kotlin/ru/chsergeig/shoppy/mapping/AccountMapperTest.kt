package ru.chsergeig.shoppy.mapping

import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import ru.chsergeig.shoppy.ShoppyTest
import ru.chsergeig.shoppy.jooq.enums.AccountRole
import ru.chsergeig.shoppy.jooq.enums.Status
import ru.chsergeig.shoppy.jooq.tables.pojos.Accounts
import ru.chsergeig.shoppy.model.AdminAccountDto
import java.util.UUID
import kotlin.random.Random

@ShoppyTest
internal class AccountMapperTest {

    @Autowired
    lateinit var mapper: AccountMapper

    @Autowired
    lateinit var enumMapper: EnumMapper

    @Test
    fun mapAdminDtoTest() {
        assertNull(mapper.mapAdmin(null as AdminAccountDto?))

        val dto = AdminAccountDto()
        var pojo: Accounts = mapper.mapAdmin(dto)
        assertAll(
            { assertNull(pojo.id) },
            { assertNull(pojo.login) },
            { assertNull(pojo.password) },
            { assertNull(pojo.salted) },
            { assertNull(pojo.status) }
        )
        dto.id = Random.nextInt()
        dto.login = UUID.randomUUID().toString()
        dto.password = UUID.randomUUID().toString()
        dto.salted = false
        dto.status = ru.chsergeig.shoppy.model.Status.aDDED
        dto.accountRoles = listOf(AccountRole.ROLE_GUEST, AccountRole.ROLE_ADMIN)
        pojo = mapper.mapAdmin(dto)
        assertAll(
            { assertEquals(dto.id, pojo.id) },
            { assertEquals(dto.login, pojo.login) },
            { assertEquals(dto.password, pojo.password) },
            { assertEquals(dto.salted, pojo.salted) },
            { assertEquals(dto.status, enumMapper.fromJooq(pojo.status)) }
        )
    }

    @Test
    fun mapAdminPojoTest() {
        assertNull(mapper.mapAdmin(null as Accounts?))

        val pojo = Accounts()
        var dto: AdminAccountDto = mapper.mapAdmin(pojo)
        assertAll(
            { assertNull(dto.id) },
            { assertNull(dto.login) },
            { assertNull(dto.password) },
            { assertNull(dto.salted) },
            { assertNull(dto.status) }
        )
        pojo.id = Random.nextInt()
        pojo.login = UUID.randomUUID().toString()
        pojo.password = UUID.randomUUID().toString()
        pojo.salted = false
        pojo.status = Status.ADDED
        dto = mapper.mapAdmin(pojo)
        assertAll(
            { assertEquals(pojo.id, dto.id) },
            { assertEquals(pojo.login, dto.login) },
            { assertEquals(pojo.password, dto.password) },
            { assertEquals(pojo.salted, dto.salted) },
            { assertEquals(pojo.status, enumMapper.toJooq(dto.status)) }
        )
    }
}
