package ru.chsergeig.shoppy.mapping

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import ru.chsergeig.shoppy.ShoppyTest

@ShoppyTest
internal class EnumMapperTest {

    @Autowired
    lateinit var mapper: EnumMapper

    @Test
    fun toJooqRoleTest() {
        Assertions.assertAll(
            {
                Assertions.assertNull(
                    mapper.toJooq(null as ru.chsergeig.shoppy.model.AccountRole?)
                )
            },
            {
                Assertions.assertEquals(
                    ru.chsergeig.shoppy.jooq.enums.AccountRole.ROLE_ADMIN,
                    mapper.toJooq(ru.chsergeig.shoppy.model.AccountRole.aDMIN)
                )
            },
            {
                Assertions.assertEquals(
                    ru.chsergeig.shoppy.jooq.enums.AccountRole.ROLE_GUEST,
                    mapper.toJooq(ru.chsergeig.shoppy.model.AccountRole.gUEST)
                )
            },
            {
                Assertions.assertEquals(
                    ru.chsergeig.shoppy.jooq.enums.AccountRole.ROLE_USER,
                    mapper.toJooq(ru.chsergeig.shoppy.model.AccountRole.uSER)
                )
            }
        )
    }

    @Test
    fun fromJooqRoleTest() {
        Assertions.assertAll(
            {
                Assertions.assertNull(
                    mapper.fromJooq(null as ru.chsergeig.shoppy.jooq.enums.AccountRole?)
                )
            },
            {
                Assertions.assertEquals(
                    ru.chsergeig.shoppy.model.AccountRole.aDMIN,
                    mapper.fromJooq(ru.chsergeig.shoppy.jooq.enums.AccountRole.ROLE_ADMIN)
                )
            },
            {
                Assertions.assertEquals(
                    ru.chsergeig.shoppy.model.AccountRole.gUEST,
                    mapper.fromJooq(ru.chsergeig.shoppy.jooq.enums.AccountRole.ROLE_GUEST)
                )
            },
            {
                Assertions.assertEquals(
                    ru.chsergeig.shoppy.model.AccountRole.uSER,
                    mapper.fromJooq(ru.chsergeig.shoppy.jooq.enums.AccountRole.ROLE_USER)
                )
            }
        )
    }

    @Test
    fun toJooqStatusTest() {
        Assertions.assertAll(
            {
                Assertions.assertNull(
                    mapper.toJooq(null as ru.chsergeig.shoppy.model.Status?)
                )
            },
            {
                Assertions.assertEquals(
                    ru.chsergeig.shoppy.jooq.enums.Status.ACTIVE,
                    mapper.toJooq(ru.chsergeig.shoppy.model.Status.aCTIVE)
                )
            },
            {
                Assertions.assertEquals(
                    ru.chsergeig.shoppy.jooq.enums.Status.ADDED,
                    mapper.toJooq(ru.chsergeig.shoppy.model.Status.aDDED)
                )
            },
            {
                Assertions.assertEquals(
                    ru.chsergeig.shoppy.jooq.enums.Status.DISABLED,
                    mapper.toJooq(ru.chsergeig.shoppy.model.Status.dISABLED)
                )
            },
            {
                Assertions.assertEquals(
                    ru.chsergeig.shoppy.jooq.enums.Status.REMOVED,
                    mapper.toJooq(ru.chsergeig.shoppy.model.Status.rEMOVED)
                )
            }
        )
    }

    @Test
    fun fromJooqStatusTest() {
        Assertions.assertAll(
            {
                Assertions.assertNull(
                    mapper.fromJooq(null as ru.chsergeig.shoppy.jooq.enums.Status?)
                )
            },
            {
                Assertions.assertEquals(
                    ru.chsergeig.shoppy.model.Status.aCTIVE,
                    mapper.fromJooq(ru.chsergeig.shoppy.jooq.enums.Status.ACTIVE)
                )
            },
            {
                Assertions.assertEquals(
                    ru.chsergeig.shoppy.model.Status.aDDED,
                    mapper.fromJooq(ru.chsergeig.shoppy.jooq.enums.Status.ADDED)
                )
            },
            {
                Assertions.assertEquals(
                    ru.chsergeig.shoppy.model.Status.dISABLED,
                    mapper.fromJooq(ru.chsergeig.shoppy.jooq.enums.Status.DISABLED)
                )
            },
            {
                Assertions.assertEquals(
                    ru.chsergeig.shoppy.model.Status.rEMOVED,
                    mapper.fromJooq(ru.chsergeig.shoppy.jooq.enums.Status.REMOVED)
                )
            }
        )
    }
}
