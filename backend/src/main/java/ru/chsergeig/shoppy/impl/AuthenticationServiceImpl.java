package ru.chsergeig.shoppy.impl;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.chsergeig.shoppy.component.TokenUtilComponent;
import ru.chsergeig.shoppy.dao.JwtTokenRepository;
import ru.chsergeig.shoppy.dto.jwt.ResponseDto;
import ru.chsergeig.shoppy.exception.ServiceException;
import ru.chsergeig.shoppy.jooq.enums.Status;
import ru.chsergeig.shoppy.jooq.tables.pojos.JwtTokens;
import ru.chsergeig.shoppy.model.JwtUserDetails;
import ru.chsergeig.shoppy.service.AuthenticationService;

import java.time.ZoneOffset;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenRepository jwtTokenRepository;
    private final TokenUtilComponent tokenUtilComponent;
    private final UserDetailsService userDetailsService;

    @Override
    public @NotNull ResponseDto authenticate(String login, String password) {
        try {
            Objects.requireNonNull(login, "Given login is null");
            Objects.requireNonNull(password, "Given password is null");
        } catch (NullPointerException npe) {
            throw new ServiceException(npe.getMessage(), npe);
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login, password
                )
        );
        UserDetails userDetails = userDetailsService.loadUserByUsername(login);
        if (userDetails instanceof JwtUserDetails) {
            String token = tokenUtilComponent.generateToken((JwtUserDetails) userDetails);
            jwtTokenRepository.addToken(
                    token,
                    tokenUtilComponent.getExpirationTimeFromToken(token).atOffset(ZoneOffset.ofHours(3)),
                    Status.ACTIVE
            );
            return new ResponseDto(
                    token,
                    tokenUtilComponent.getExpirationTimeFromToken(token)
            );
        }
        throw new ServiceException(
                "Cant authenticate user"
        );
    }

    @Override
    public Boolean probeToken(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(tokenUtilComponent.getUsernameFromToken(token));
        if (userDetails instanceof JwtUserDetails) {
            JwtTokens storedToken = jwtTokenRepository.getToken(token);
            if (storedToken == null || storedToken.getStatus() != Status.ACTIVE) {
                return false;
            }
            return tokenUtilComponent.isTokenValid(token, (JwtUserDetails) userDetails);
        }
        return false;
    }

    @Override
    public void logout(@Nullable String token) {
        SecurityContextHolder.getContext().setAuthentication(null);
        jwtTokenRepository.setTokenRemoved(token);
    }
}
