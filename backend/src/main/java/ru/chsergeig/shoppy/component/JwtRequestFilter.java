package ru.chsergeig.shoppy.component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.chsergeig.shoppy.model.JwtUserDetails;
import ru.chsergeig.shoppy.properties.SecurityProperties;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component("jwtRequestFilter")
@Profile("!test")
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final SecurityProperties securityProperties;
    private final TokenUtilComponent tokenUtilComponent;
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
        if (login != null && SecurityContextHolder.getContext().getAuthentication() == null) {
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
