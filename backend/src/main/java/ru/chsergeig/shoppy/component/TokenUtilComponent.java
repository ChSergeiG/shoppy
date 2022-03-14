package ru.chsergeig.shoppy.component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.chsergeig.shoppy.model.JwtUserDetails;
import ru.chsergeig.shoppy.properties.SecurityProperties;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Function;

import static io.jsonwebtoken.impl.TextCodec.BASE64;

@Component
@RequiredArgsConstructor
public class TokenUtilComponent {

    private static final Charset CHARSET = StandardCharsets.UTF_8;
    private static final ZoneId ZONE_ID = ZoneId.of("UTC");
    private static final ZoneOffset ZONE_OFFSET = ZoneOffset.UTC;

    private final SecurityProperties securityProperties;

    public String generateToken(JwtUserDetails userDetails) {
        final LocalDateTime createdDate = LocalDateTime.now();
        final LocalDateTime expirationDate = createdDate.plus(
                securityProperties.getJwt().getExpirationInSeconds(),
                ChronoUnit.SECONDS
        );
        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(userDetails.getUsername())
                .setIssuedAt(Date.from(createdDate.toInstant(ZONE_OFFSET)))
                .setExpiration(Date.from(expirationDate.toInstant(ZONE_OFFSET)))
                .signWith(SignatureAlgorithm.HS512, BASE64.encode(securityProperties.getJwt().getSecret().getBytes(CHARSET)))
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public LocalDateTime getExpirationTimeFromToken(String token) {
        return LocalDateTime.ofInstant(getClaimFromToken(token, Claims::getExpiration).toInstant(), ZONE_ID);
    }

    public boolean isTokenValid(String tokenValue, JwtUserDetails userDetails) {
        final String loginFromToken = getUsernameFromToken(tokenValue);
        return Objects.equals(loginFromToken, userDetails.getUsername())
                && !isTokenExpired(tokenValue);

    }

    public boolean isTokenExpired(String tokenValue) {
        Date tokenExpirationDate = getClaimFromToken(tokenValue, Claims::getExpiration);
        return LocalDateTime
                .ofInstant(tokenExpirationDate.toInstant(), ZONE_ID)
                .isBefore(LocalDateTime.now());
    }

    public <T> T getClaimFromToken(
            String token,
            Function<Claims, T> claimExtractor
    ) {
        Claims claims = Jwts.parser()
                .setSigningKey(BASE64.encode(securityProperties.getJwt().getSecret().getBytes(CHARSET)))
                .parseClaimsJws(token)
                .getBody();
        return claimExtractor.apply(claims);
    }

}
