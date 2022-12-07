package ru.chsergeig.shoppy.utils

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.impl.TextCodec
import org.springframework.http.HttpStatus
import org.springframework.util.StringUtils
import ru.chsergeig.shoppy.exception.ControllerException
import ru.chsergeig.shoppy.model.JwtUserDetails
import ru.chsergeig.shoppy.properties.SecurityProperties
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import java.util.Date
import java.util.function.Function

class TokenUtils private constructor() {

    companion object {
        @JvmStatic
        private val CHARSET: Charset = StandardCharsets.UTF_8

        @JvmStatic
        private val ZONE_ID: ZoneId = ZoneId.of("UTC")

        @JvmStatic
        public val ZONE_OFFSET: ZoneOffset = ZoneOffset.UTC

        @JvmStatic
        fun generateToken(
            securityProperties: SecurityProperties,
            userDetails: JwtUserDetails
        ): String? {
            val createdDate = LocalDateTime.now()
            val expirationDate = createdDate.plus(
                securityProperties.jwt.expirationInSeconds,
                ChronoUnit.SECONDS
            )
            return Jwts.builder()
                .setClaims(HashMap())
                .setSubject(userDetails.username)
                .setIssuedAt(Date.from(createdDate.toInstant(ZONE_OFFSET)))
                .setExpiration(Date.from(expirationDate.toInstant(ZONE_OFFSET)))
                .signWith(
                    SignatureAlgorithm.HS512,
                    TextCodec.BASE64.encode(securityProperties.jwt.secret.toByteArray(CHARSET))
                )
                .compact()
        }

        @JvmStatic
        fun getUsernameFromToken(
            securityProperties: SecurityProperties,
            token: String?
        ): String? {
            return getClaimFromToken(
                securityProperties,
                token
            ) { it?.subject }
        }

        @JvmStatic
        fun getExpirationTimeFromToken(
            securityProperties: SecurityProperties,
            token: String?
        ): OffsetDateTime {
            return LocalDateTime.ofInstant(
                getClaimFromToken(
                    securityProperties,
                    token
                ) { it?.expiration }!!.toInstant(),
                ZONE_ID
            ).atOffset(ZONE_OFFSET)
        }

        @JvmStatic
        fun isTokenValid(
            securityProperties: SecurityProperties,
            tokenValue: String?,
            userDetails: JwtUserDetails
        ): Boolean {
            val loginFromToken = getUsernameFromToken(securityProperties, tokenValue)
            return (
                loginFromToken == userDetails.username &&
                    !isTokenExpired(securityProperties, tokenValue)
                )
        }

        @JvmStatic
        fun isTokenExpired(
            securityProperties: SecurityProperties,
            tokenValue: String?
        ): Boolean {
            return getExpirationTimeFromToken(securityProperties, tokenValue).toEpochSecond() <
                OffsetDateTime.now().toEpochSecond() + OffsetDateTime.now().offset.totalSeconds
        }

        @JvmStatic
        fun <T> getClaimFromToken(
            securityProperties: SecurityProperties,
            token: String?,
            claimExtractor: Function<Claims?, T>
        ): T {
            val claims = Jwts.parser()
                .setSigningKey(TextCodec.BASE64.encode(securityProperties.jwt.secret.toByteArray(CHARSET)))
                .parseClaimsJws(token)
                .body
            return claimExtractor.apply(claims)
        }

        @JvmStatic
        fun validateTokenAndGetUsername(
            securityProperties: SecurityProperties,
            token: String?
        ): String? {
            if (!StringUtils.hasText(token) || isTokenExpired(securityProperties, token)) {
                throw ControllerException(
                    HttpStatus.UNAUTHORIZED,
                    "You are not authorized",
                    null
                )
            }
            return getUsernameFromToken(securityProperties, token)
        }

        @JvmStatic
        fun saltValue(
            securityProperties: SecurityProperties,
            valueToSalt: String
        ): String = SecretUtils.hashValue(
            valueToSalt.toCharArray(),
            securityProperties.jwt.salt.encodeToByteArray()
        )

        @JvmStatic
        fun verifySaltedValue(
            securityProperties: SecurityProperties,
            valueToVerify: String,
            valueVerifyTo: String
        ): Boolean = SecretUtils.verifyHashed(
            valueToVerify.toCharArray(),
            valueVerifyTo.toCharArray(),
            securityProperties.jwt.salt.encodeToByteArray()
        )
    }
}
