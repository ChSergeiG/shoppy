package ru.chsergeig.shoppy.mapping

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import ru.chsergeig.shoppy.ShoppyTest
import ru.chsergeig.shoppy.jooq.enums.Status
import ru.chsergeig.shoppy.jooq.tables.pojos.Goods
import ru.chsergeig.shoppy.model.AdminGoodDto
import ru.chsergeig.shoppy.model.CommonGoodDto
import java.util.UUID
import kotlin.random.Random

@ShoppyTest
class GoodMapperTest {

    @Autowired
    lateinit var enumMapper: EnumMapper

    @Autowired
    lateinit var mapper: GoodMapper

    @Test
    fun mapAdminPojoTest() {
        Assertions.assertNull(mapper.mapAdmin(null as Goods?))

        val pojo = Goods()
        var dto: AdminGoodDto = mapper.mapAdmin(pojo)

        Assertions.assertAll(
            { Assertions.assertNull(dto.id) },
            { Assertions.assertNull(dto.name) },
            { Assertions.assertNull(dto.article) },
            { Assertions.assertNull(dto.price) },
            { Assertions.assertNull(dto.status) }
        )

        pojo.id = Random.nextInt()
        pojo.name = UUID.randomUUID().toString()
        pojo.article = UUID.randomUUID().toString()
        pojo.price = Random.nextDouble().toBigDecimal()
        pojo.status = Status.ADDED
        dto = mapper.mapAdmin(pojo)

        Assertions.assertAll(
            { Assertions.assertEquals(pojo.id, dto.id) },
            { Assertions.assertEquals(pojo.name, dto.name) },
            { Assertions.assertEquals(pojo.article, dto.article) },
            { Assertions.assertEquals(pojo.price, dto.price) },
            { Assertions.assertEquals(pojo.status, enumMapper.toJooq(dto.status)) }
        )
    }

    @Test
    fun mapAdminDtoTest() {
        Assertions.assertNull(mapper.mapAdmin(null as AdminGoodDto?))

        val dto = AdminGoodDto()

        var pojo: Goods = mapper.mapAdmin(dto)
        Assertions.assertAll(
            { Assertions.assertNull(pojo.id) },
            { Assertions.assertNull(pojo.name) },
            { Assertions.assertNull(pojo.article) },
            { Assertions.assertNull(pojo.price) },
            { Assertions.assertNull(pojo.status) }
        )
        dto.name = UUID.randomUUID().toString()
        dto.status = ru.chsergeig.shoppy.model.Status.aDDED
        dto.article = UUID.randomUUID().toString()
        dto.price = Random.nextDouble().toBigDecimal()
        dto.id = Random.nextInt()

        pojo = mapper.mapAdmin(dto)
        Assertions.assertAll(
            { Assertions.assertEquals(dto.name, pojo.name) },
            { Assertions.assertEquals(dto.status, enumMapper.fromJooq(pojo.status)) },
            { Assertions.assertEquals(dto.article, pojo.article) },
            { Assertions.assertEquals(dto.price, pojo.price) },
            { Assertions.assertEquals(dto.id, pojo.id) }
        )
    }

    @Test
    fun mapCommonPojoTest() {
        Assertions.assertNull(mapper.mapCommon(null as Goods?))

        val pojo = Goods()
        var dto: CommonGoodDto = mapper.mapCommon(pojo)

        Assertions.assertAll(
            { Assertions.assertNull(dto.name) },
            { Assertions.assertNull(dto.article) },
            { Assertions.assertNull(dto.status) },
            { Assertions.assertNull(dto.price) }
        )

        pojo.id = Random.nextInt()
        pojo.name = UUID.randomUUID().toString()
        pojo.article = UUID.randomUUID().toString()
        pojo.price = Random.nextDouble().toBigDecimal()
        pojo.status = Status.ADDED
        dto = mapper.mapCommon(pojo)

        Assertions.assertAll(
            { Assertions.assertEquals(pojo.name, dto.name) },
            { Assertions.assertEquals(pojo.article, dto.article) },
            { Assertions.assertEquals(pojo.status, enumMapper.toJooq(dto.status)) },
            { Assertions.assertEquals(pojo.price, dto.price) }
        )
    }

    @Test
    fun mapAdminCountedPojoTest() {
        Assertions.assertNull(mapper.mapCommon(null as Goods?))

        val pojo = Goods()
        var dto: CommonGoodDto = mapper.mapCommon(pojo)

        Assertions.assertAll(
            { Assertions.assertNull(dto.name) },
            { Assertions.assertNull(dto.article) },
            { Assertions.assertNull(dto.status) },
            { Assertions.assertNull(dto.price) }
        )

        pojo.id = Random.nextInt()
        pojo.name = UUID.randomUUID().toString()
        pojo.article = UUID.randomUUID().toString()
        pojo.price = Random.nextDouble().toBigDecimal()
        pojo.status = Status.ADDED
        dto = mapper.mapCommon(pojo)

        Assertions.assertAll(
            { Assertions.assertEquals(pojo.name, dto.name) },
            { Assertions.assertEquals(pojo.article, dto.article) },
            { Assertions.assertEquals(pojo.status, enumMapper.toJooq(dto.status)) },
            { Assertions.assertEquals(pojo.price, dto.price) }
        )
    }

    //
//    Goods mapCommon(CommonGoodDto dto);
    @Test
    fun mapCommonDtoTest() {
        Assertions.assertNull(mapper.mapCommon(null as CommonGoodDto?))

        val dto = CommonGoodDto()

        var pojo: Goods = mapper.mapCommon(dto)
        Assertions.assertAll(
            { Assertions.assertNull(pojo.id) },
            { Assertions.assertNull(pojo.name) },
            { Assertions.assertNull(pojo.article) },
            { Assertions.assertNull(pojo.price) },
            { Assertions.assertNull(pojo.status) }
        )
        dto.name = UUID.randomUUID().toString()
        dto.status = ru.chsergeig.shoppy.model.Status.aDDED
        dto.article = UUID.randomUUID().toString()
        dto.price = Random.nextDouble().toBigDecimal()

        pojo = mapper.mapCommon(dto)
        Assertions.assertAll(
            { Assertions.assertEquals(dto.name, pojo.name) },
            { Assertions.assertEquals(dto.status, enumMapper.fromJooq(pojo.status)) },
            { Assertions.assertEquals(dto.article, pojo.article) },
            { Assertions.assertEquals(dto.price, pojo.price) }
        )
    }
}
