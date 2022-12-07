package ru.chsergeig.shoppy.utils

import java.security.NoSuchAlgorithmException
import java.security.spec.InvalidKeySpecException
import java.util.Base64
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

class SecretUtils private constructor() {

    companion object {
        @JvmStatic
        fun hashValue(
            password: CharArray,
            salt: ByteArray
        ): String {
            val spec = PBEKeySpec(password, salt, 10_000, 0x100)
            return try {
                val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
                Base64.getEncoder().encodeToString(factory.generateSecret(spec).encoded)
            } catch (e: NoSuchAlgorithmException) {
                throw AssertionError("Error while hashing a password.", e)
            } catch (e: InvalidKeySpecException) {
                throw AssertionError("Error while hashing a password.", e)
            } finally {
                spec.clearPassword()
            }
        }

        @JvmStatic
        fun verifyHashed(
            passwordToVerify: CharArray,
            securedPassword: CharArray,
            salt: ByteArray
        ): Boolean {
            val newSecurePassword = generateSecurePassword(passwordToVerify, salt)
            return newSecurePassword.contentEquals(securedPassword)
        }

        private fun generateSecurePassword(
            password: CharArray,
            salt: ByteArray
        ): CharArray {
            return hashValue(password, salt).toCharArray()
        }
    }
}
