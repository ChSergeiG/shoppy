package ru.chsergeig.shoppy.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "shoppy.security")
public class SecurityProperties {

    private Jwt jwt;
    private User user;

    @Data
    public static class Jwt {
        private String authorizationHeader;
        private String secret;
        private Long expirationInSeconds;
    }

    @Data
    public static class User {
        private String login;
        private String password;
    }
}
