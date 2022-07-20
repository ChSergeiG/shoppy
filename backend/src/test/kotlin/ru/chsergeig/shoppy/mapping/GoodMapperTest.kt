package ru.chsergeig.shoppy.mapping

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import ru.chsergeig.shoppy.ShoppyTest
import ru.chsergeig.shoppy.dto.admin.GoodDto
import ru.chsergeig.shoppy.jooq.enums.Status
import ru.chsergeig.shoppy.jooq.tables.pojos.Goods
import java.util.UUID
import kotlin.random.Random

@ShoppyTest
class GoodMapperTest {

    @Autowired
    lateinit var mapper: GoodMapper

    @Test
    fun dtoToPojo() {
        val dto = GoodDto()
        Assertions.assertDoesNotThrow { mapper.map(dto) }
        var pojo: Goods = mapper.map(dto)
        Assertions.assertAll(
            { Assertions.assertNull(pojo.id) },
            { Assertions.assertNull(pojo.name) },
            { Assertions.assertNull(pojo.article) },
            { Assertions.assertNull(pojo.price) },
            { Assertions.assertNull(pojo.status) }
        )
        dto.id = Random.nextInt()
        dto.name = UUID.randomUUID().toString()
        dto.article = UUID.randomUUID().toString()
        dto.status = Status.ADDED
        dto.price = Random.nextDouble().toBigDecimal()

        pojo = mapper.map(dto)
        Assertions.assertAll(
            { Assertions.assertEquals(dto.id, pojo.id) },
            { Assertions.assertEquals(dto.name, pojo.name) },
            { Assertions.assertEquals(dto.article, pojo.article) },
            { Assertions.assertEquals(dto.price, pojo.price) },
            { Assertions.assertEquals(dto.status, pojo.status) }
        )
    }

    @Test
    fun pojoToDto() {
        val pojo = Goods()
        Assertions.assertDoesNotThrow { mapper.map(pojo) }
        var dto: GoodDto = mapper.map(pojo)
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
        dto = mapper.map(pojo)
        Assertions.assertAll(
            { Assertions.assertEquals(dto.id, pojo.id) },
            { Assertions.assertEquals(dto.name, pojo.name) },
            { Assertions.assertEquals(dto.article, pojo.article) },
            { Assertions.assertEquals(dto.price, pojo.price) },
            { Assertions.assertEquals(dto.status, pojo.status) }
        )
    }

    @Test
    fun mapList() {
        var listToMap: MutableList<Goods>? = null
        Assertions.assertDoesNotThrow { mapper.mapList(listToMap) }
        Assertions.assertNull(mapper.mapList(listToMap))
        listToMap = mutableListOf()
        Assertions.assertTrue(mapper.mapList(listToMap).isEmpty())
        listToMap.add(Goods())
        Assertions.assertEquals(1, mapper.mapList(listToMap).size)
        listToMap.add(Goods())
        Assertions.assertEquals(2, mapper.mapList(listToMap).size)
    }
}
