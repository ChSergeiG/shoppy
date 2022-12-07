package ru.chsergeig.shoppy.component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.chsergeig.shoppy.dao.JwtTokenRepository;
import ru.chsergeig.shoppy.jooq.enums.Status;
import ru.chsergeig.shoppy.jooq.tables.pojos.JwtTokens;
import ru.chsergeig.shoppy.model.JwtUserDetails;
import ru.chsergeig.shoppy.properties.SecurityProperties;
import ru.chsergeig.shoppy.utils.TokenUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component("jwtRequestFilter")
@Profile("!test")
public class JwtRequestFilter extends OncePerRequestFilter {

    private final SecurityProperties securityProperties;
    private final JwtTokenRepository jwtTokenRepository;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        // если токен есть - проверяяем валидность и все
        // если токена нет - нужно проверить пару логин-пасс и контекста и провалидировать
        // если валидация ок - выпустить новый токен и вернуть пользователю
        final String tokenValue = request.getHeader(securityProperties.getJwt().getAuthorizationHeader());
        if (!StringUtils.hasText(tokenValue)) {
            filterChain.doFilter(request, response);
            return;
        }

        String login;
        try {
            login = TokenUtils.getUsernameFromToken(securityProperties, tokenValue);
        } catch (IllegalArgumentException iae) {
            log.warn("Illegal JWT token value");
            SecurityContextHolder.getContext().setAuthentication(null);
            filterChain.doFilter(request, response);
            return;
        } catch (ExpiredJwtException eje) {
            log.warn("JWT token expired");
            SecurityContextHolder.getContext().setAuthentication(null);
            filterChain.doFilter(request, response);
            return;
        } catch (MalformedJwtException ignore) {
            log.warn("Malformed JWT token");
            SecurityContextHolder.getContext().setAuthentication(null);
            filterChain.doFilter(request, response);
            return;
        }
        JwtTokens token = jwtTokenRepository.getToken(tokenValue);
        if (token == null || token.getStatus() != Status.ACTIVE) {
            SecurityContextHolder.getContext().setAuthentication(null);
        } else if (login != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(login);
            if (
                    userDetails instanceof JwtUserDetails
                            && TokenUtils.isTokenValid(securityProperties, tokenValue, (JwtUserDetails) userDetails)
            ) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
