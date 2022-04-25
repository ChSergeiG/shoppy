package ru.chsergeig.shoppy.mapping

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import ru.chsergeig.shoppy.ShoppyTest
import ru.chsergeig.shoppy.dto.admin.OrderDto
import ru.chsergeig.shoppy.jooq.enums.Status
import ru.chsergeig.shoppy.jooq.tables.pojos.Orders
import java.util.UUID
import kotlin.random.Random

@ShoppyTest
open class OrderMapperTest {

    @Autowired
    lateinit var mapper: OrderMapper

    @Test
    fun dtoToPojo() {
        val dto = OrderDto()
        Assertions.assertDoesNotThrow { mapper.map(dto) }
        var pojo: Orders = mapper.map(dto)
        Assertions.assertAll(
            { Assertions.assertNull(pojo.id) },
            { Assertions.assertNull(pojo.info) },
            { Assertions.assertNull(pojo.status) },
            { Assertions.assertNull(pojo.guid) }
        )
        dto.id = Random.nextInt()
        dto.info = UUID.randomUUID().toString()
        dto.status = Status.ADDED
        dto.guid = UUID.randomUUID().toString()

        pojo = mapper.map(dto)
        Assertions.assertAll(
            { Assertions.assertEquals(dto.id, pojo.id) },
            { Assertions.assertEquals(dto.info, pojo.info) },
            { Assertions.assertEquals(dto.status, pojo.status) },
            { Assertions.assertEquals(dto.guid, pojo.guid) }
        )
    }

    @Test
    fun pojoToDto() {
        val pojo = Orders()
        Assertions.assertDoesNotThrow { mapper.map(pojo) }
        var dto: OrderDto = mapper.map(pojo)
        Assertions.assertAll(
            { Assertions.assertNull(dto.id) },
            { Assertions.assertNull(dto.info) },
            { Assertions.assertNull(dto.status) },
            { Assertions.assertNull(dto.guid) }
        )
        pojo.id = Random.nextInt()
        pojo.info = UUID.randomUUID().toString()
        pojo.status = Status.ADDED
        pojo.guid = UUID.randomUUID().toString()

        dto = mapper.map(pojo)
        Assertions.assertAll(
            { Assertions.assertEquals(dto.id, pojo.id) },
            { Assertions.assertEquals(dto.info, pojo.info) },
            { Assertions.assertEquals(dto.status, pojo.status) },
            { Assertions.assertEquals(dto.guid, pojo.guid) }
        )
    }

}