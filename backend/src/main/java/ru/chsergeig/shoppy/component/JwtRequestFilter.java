package ru.chsergeig.shoppy.component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.chsergeig.shoppy.dao.JwtTokenRepository;
import ru.chsergeig.shoppy.exception.ControllerException;
import ru.chsergeig.shoppy.jooq.enums.Status;
import ru.chsergeig.shoppy.jooq.tables.pojos.JwtTokens;
import ru.chsergeig.shoppy.model.JwtUserDetails;
import ru.chsergeig.shoppy.properties.SecurityProperties;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
@Component("jwtRequestFilter")
@Profile("!test")
public class JwtRequestFilter extends OncePerRequestFilter {

    private final SecurityProperties securityProperties;
    private final TokenUtilComponent tokenUtilComponent;
    private final JwtTokenRepository jwtTokenRepository;
    private final UserDetailsService userDetailsService;

    @Override
    @SneakyThrows
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) {
        final String tokenValue = request.getHeader(securityProperties.getJwt().getAuthorizationHeader());
        String login = null;
        if (tokenValue != null) {
            try {
                login = tokenUtilComponent.getUsernameFromToken(tokenValue);
            } catch (IllegalArgumentException iae) {
                log.warn("Illegal JWT token value");
            } catch (ExpiredJwtException eje) {
                log.warn("JWT token expired");
            } catch (MalformedJwtException ignore) {
            }
        }
        JwtTokens token = jwtTokenRepository.getToken(tokenValue);
        if (token == null || token.getStatus() != Status.ACTIVE) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            SecurityContextHolder.getContext().setAuthentication(null);
        } else if (login != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(login);
            if (userDetails instanceof JwtUserDetails
                    && tokenUtilComponent.isTokenValid(tokenValue, (JwtUserDetails) userDetails)
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
