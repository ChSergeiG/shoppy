package ru.chsergeig.shoppy.mapping

import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import ru.chsergeig.shoppy.ShoppyTest
import ru.chsergeig.shoppy.dto.admin.AccountDto
import ru.chsergeig.shoppy.jooq.enums.AccountRole
import ru.chsergeig.shoppy.jooq.enums.Status
import ru.chsergeig.shoppy.jooq.tables.pojos.Accounts
import java.util.UUID
import kotlin.random.Random

@ShoppyTest
open class AccountMapperTest {

    @Autowired
    lateinit var mapper: AccountMapper

    @Test
    fun dtoToPojo() {
        val dto = AccountDto()
        assertDoesNotThrow { mapper.map(dto) }
        var pojo: Accounts = mapper.map(dto)
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
        dto.status = Status.ADDED
        dto.accountRoles = listOf(AccountRole.ROLE_GUEST, AccountRole.ROLE_ADMIN)
        pojo = mapper.map(dto)
        assertAll(
            { assertEquals(dto.id, pojo.id) },
            { assertEquals(dto.login, pojo.login) },
            { assertEquals(dto.password, pojo.password) },
            { assertEquals(dto.salted, pojo.salted) },
            { assertEquals(dto.status, pojo.status) }
        )
    }

    @Test
    fun pojoToDto() {
        val pojo = Accounts()
        assertDoesNotThrow { mapper.map(pojo) }
        var dto: AccountDto = mapper.map(pojo)
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
        dto = mapper.map(pojo)
        assertAll(
            { assertEquals(pojo.id, dto.id) },
            { assertEquals(pojo.login, dto.login) },
            { assertEquals(pojo.password, dto.password) },
            { assertEquals(pojo.salted, dto.salted) },
            { assertEquals(pojo.status, dto.status) }
        )
    }

    @Test
    fun mapList() {
        var listToMap: MutableList<Accounts>? = null
        assertDoesNotThrow { mapper.mapList(listToMap) }
        assertNull(mapper.mapList(listToMap))
        listToMap = mutableListOf()
        assertTrue(mapper.mapList(listToMap).isEmpty())
        listToMap.add(Accounts())
        assertEquals(1, mapper.mapList(listToMap).size)
        listToMap.add(Accounts())
        assertEquals(2, mapper.mapList(listToMap).size)
    }
}
