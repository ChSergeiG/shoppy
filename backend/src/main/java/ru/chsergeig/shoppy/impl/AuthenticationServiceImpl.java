package ru.chsergeig.shoppy.impl;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.chsergeig.shoppy.dao.JwtTokenRepository;
import ru.chsergeig.shoppy.exception.ServiceException;
import ru.chsergeig.shoppy.jooq.enums.Status;
import ru.chsergeig.shoppy.jooq.tables.pojos.JwtTokens;
import ru.chsergeig.shoppy.model.JwtResponseDto;
import ru.chsergeig.shoppy.model.JwtUserDetails;
import ru.chsergeig.shoppy.properties.SecurityProperties;
import ru.chsergeig.shoppy.service.AuthenticationService;
import ru.chsergeig.shoppy.utils.TokenUtils;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenRepository jwtTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final SecurityProperties securityProperties;

    @NotNull
    @Override
    public JwtResponseDto authenticate(
            @NotNull String login,
            @NotNull String password
    ) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(login);
        if (!passwordEncoder.matches(securityProperties.getJwt().getSalt() + "$$" + password, userDetails.getPassword())) {
            throw new ServiceException("Incorrect credentials");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login, securityProperties.getJwt().getSalt() + "$$" + password)
        );

        if (userDetails instanceof JwtUserDetails) {
            String token = TokenUtils.generateToken(securityProperties, (JwtUserDetails) userDetails);
            jwtTokenRepository.addToken(
                    token,
                    TokenUtils.getExpirationTimeFromToken(securityProperties, token),
                    Status.ACTIVE
            );
            return new JwtResponseDto(
                    token,
                    TokenUtils.getExpirationTimeFromToken(securityProperties, token)
            );
        }
        throw new ServiceException("Cant authenticate user");
    }

    @Override
    public Boolean probeToken(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(
                TokenUtils.getUsernameFromToken(securityProperties, token)
        );
        if (userDetails instanceof JwtUserDetails) {
            JwtTokens storedToken = jwtTokenRepository.getToken(token);
            if (storedToken == null || storedToken.getStatus() != Status.ACTIVE) {
                return false;
            }
            return TokenUtils.isTokenValid(securityProperties, token, (JwtUserDetails) userDetails);
        }
        return false;
    }

    @Override
    public void logout(@Nullable String token) {
        SecurityContextHolder.getContext().setAuthentication(null);
        jwtTokenRepository.setTokenRemoved(token);
    }
}
