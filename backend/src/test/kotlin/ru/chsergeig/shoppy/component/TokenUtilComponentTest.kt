package ru.chsergeig.shoppy.component

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import ru.chsergeig.shoppy.ShoppyTest
import ru.chsergeig.shoppy.exception.ControllerException
import ru.chsergeig.shoppy.model.JwtUserDetails
import java.util.UUID
import kotlin.random.Random

@ShoppyTest
open class TokenUtilComponentTest {

    @Autowired
    lateinit var tokenUtilComponent: TokenUtilComponent

    @Test
    fun generateTokenTest() {
        val userDetails = JwtUserDetails(
            Random.nextLong(),
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            listOf("ROLE_1", "ROLE_2")
        )
        val token = tokenUtilComponent.generateToken(userDetails)
        Assertions.assertTrue(
            tokenUtilComponent.isTokenValid(token, userDetails)
        )
        Assertions.assertEquals(
            userDetails.username,
            tokenUtilComponent.validateTokenAndGetUsername(token)
        )
        Assertions.assertFalse(
            tokenUtilComponent.isTokenValid(
                token,
                JwtUserDetails(
                    userDetails.id,
                    "_${userDetails.username}",
                    userDetails.password,
                    listOf()
                )
            )
        )

        Thread.sleep(3001)

        Assertions.assertFalse(
            tokenUtilComponent.isTokenValid(token, userDetails)
        )
        Assertions.assertThrows(ControllerException::class.java) {
            tokenUtilComponent.validateTokenAndGetUsername(token)
        }
    }
}
