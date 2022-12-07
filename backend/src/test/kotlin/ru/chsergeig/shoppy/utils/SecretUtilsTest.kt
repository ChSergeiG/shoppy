package ru.chsergeig.shoppy.utils

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.chsergeig.shoppy.ShoppyTest
import java.util.Base64
import java.util.UUID

@ShoppyTest
internal class SecretUtilsTest {

    var salt: ByteArray? = null

    @BeforeEach
    fun setup() {
        salt = Base64.getEncoder().encode(UUID.randomUUID().toString().encodeToByteArray())
    }

    @Test
    fun hashValueTest() {
        val password = UUID.randomUUID().toString().toCharArray()
        val hash = SecretUtils.hashValue(
            password,
            salt!!
        ).toCharArray()
        Assertions.assertTrue(SecretUtils.verifyHashed(password, hash, salt!!))
    }

    @Test
    fun verifyHashedTest() {
        val password1 = UUID.randomUUID().toString().toCharArray()
        val password2 = UUID.randomUUID().toString().toCharArray()
        val hash = SecretUtils.hashValue(
            password1,
            salt!!
        ).toCharArray()
        Assertions.assertTrue(SecretUtils.verifyHashed(password1, hash, salt!!))
        Assertions.assertFalse(SecretUtils.verifyHashed(password2, hash, salt!!))
        kotlin.run {
            Assertions.assertTrue(SecretUtils.verifyHashed(password1, hash, salt!!))
            Assertions.assertFalse(SecretUtils.verifyHashed(password2, hash, salt!!))
        }
    }
}
