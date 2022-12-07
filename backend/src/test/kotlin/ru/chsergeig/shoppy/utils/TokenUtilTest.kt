package ru.chsergeig.shoppy.utils

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import ru.chsergeig.shoppy.ShoppyTest
import ru.chsergeig.shoppy.exception.ControllerException
import ru.chsergeig.shoppy.model.JwtUserDetails
import ru.chsergeig.shoppy.properties.SecurityProperties
import java.util.UUID
import kotlin.random.Random

@ShoppyTest
internal class TokenUtilTest {

    @Autowired
    lateinit var securityProperties: SecurityProperties

    @Test
    fun generateTokenTest() {
        val userDetails = JwtUserDetails(
            Random.nextLong(),
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            listOf("ROLE_1", "ROLE_2")
        )
        val token = TokenUtils.generateToken(
            securityProperties,
            userDetails
        )
        Assertions.assertTrue(
            TokenUtils.isTokenValid(
                securityProperties,
                token,
                userDetails
            )
        )
        Assertions.assertEquals(
            userDetails.username,
            TokenUtils.validateTokenAndGetUsername(
                securityProperties,
                token
            )
        )
        Assertions.assertFalse(
            TokenUtils.isTokenValid(
                securityProperties,
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
            TokenUtils.isTokenValid(
                securityProperties,
                token,
                userDetails
            )
        )
        Assertions.assertThrows(ControllerException::class.java) {
            TokenUtils.validateTokenAndGetUsername(
                securityProperties,
                token
            )
        }
    }
}
