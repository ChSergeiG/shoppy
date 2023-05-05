package ru.chsergeig.shoppy.mapping

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import ru.chsergeig.shoppy.ShoppyTest
import ru.chsergeig.shoppy.jooq.enums.Status
import ru.chsergeig.shoppy.jooq.tables.pojos.Orders
import ru.chsergeig.shoppy.model.AdminOrderDto
import ru.chsergeig.shoppy.model.CommonOrderDto
import ru.chsergeig.shoppy.model.ExtendedOrderDto
import java.util.UUID
import kotlin.random.Random

@ShoppyTest
open class OrderMapperTest {

    @Autowired
    lateinit var enumMapper: EnumMapper

    @Autowired
    lateinit var mapper: OrderMapper

    @Test
    fun mapCommonDtoTest() {
        Assertions.assertNull(mapper.mapCommon(null))

        val dto = CommonOrderDto()
        var pojo: Orders = mapper.mapCommon(dto)
        Assertions.assertAll(
            { Assertions.assertNull(pojo.id) },
            { Assertions.assertNull(pojo.info) },
            { Assertions.assertNull(pojo.status) }
        )

        dto.id = Random.nextInt()
        dto.info = UUID.randomUUID().toString()
        dto.status = ru.chsergeig.shoppy.model.Status.aDDED

        pojo = mapper.mapCommon(dto)
        Assertions.assertAll(
            { Assertions.assertEquals(dto.id, pojo.id) },
            { Assertions.assertEquals(dto.info, pojo.info) },
            { Assertions.assertEquals(dto.status, enumMapper.fromJooq(pojo.status)) }
        )
    }

    @Test
    fun mapAdminPojoTest() {
        Assertions.assertNull(mapper.mapAdmin(null as Orders?))

        val pojo = Orders()
        var dto: AdminOrderDto = mapper.mapAdmin(pojo)

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

        dto = mapper.mapAdmin(pojo)
        Assertions.assertAll(
            { Assertions.assertEquals(pojo.id, dto.id) },
            { Assertions.assertEquals(pojo.info, dto.info) },
            { Assertions.assertEquals(pojo.status, enumMapper.toJooq(dto.status)) },
            { Assertions.assertEquals(pojo.guid, dto.guid) }
        )
    }

    @Test
    open fun mapExtendedPojoTest() {
        Assertions.assertNull(mapper.mapExtended(null as Orders?))

        val pojo = Orders()
        var dto: ExtendedOrderDto = mapper.mapExtended(pojo)

        Assertions.assertAll(
            { Assertions.assertNull(dto.id) },
            { Assertions.assertNull(dto.info) },
            { Assertions.assertNull(dto.status) },
            { Assertions.assertNull(dto.guid) },
            { Assertions.assertNull(dto.propertyEntries) }
        )

        pojo.id = Random.nextInt()
        pojo.info = UUID.randomUUID().toString()
        pojo.status = Status.ADDED
        pojo.guid = UUID.randomUUID().toString()

        dto = mapper.mapExtended(pojo)
        Assertions.assertAll(
            { Assertions.assertEquals(pojo.id, dto.id) },
            { Assertions.assertEquals(pojo.info, dto.info) },
            { Assertions.assertEquals(pojo.status, enumMapper.toJooq(dto.status)) },
            { Assertions.assertEquals(pojo.guid, dto.guid) },
            { Assertions.assertNull(dto.propertyEntries) }
        )
    }

    @Test
    fun mapAdminDtoTest() {
        Assertions.assertNull(mapper.mapAdmin(null as AdminOrderDto?))

        val dto = AdminOrderDto()
        var pojo: Orders = mapper.mapAdmin(dto)
        Assertions.assertAll(
            { Assertions.assertNull(pojo.id) },
            { Assertions.assertNull(pojo.info) },
            { Assertions.assertNull(pojo.status) },
            { Assertions.assertNull(pojo.guid) }
        )

        dto.id = Random.nextInt()
        dto.info = UUID.randomUUID().toString()
        dto.status = ru.chsergeig.shoppy.model.Status.aDDED
        dto.guid = UUID.randomUUID().toString()

        pojo = mapper.mapAdmin(dto)
        Assertions.assertAll(
            { Assertions.assertEquals(dto.id, pojo.id) },
            { Assertions.assertEquals(dto.info, pojo.info) },
            { Assertions.assertEquals(dto.status, enumMapper.fromJooq(pojo.status)) },
            { Assertions.assertEquals(dto.guid, pojo.guid) }
        )
    }
}
