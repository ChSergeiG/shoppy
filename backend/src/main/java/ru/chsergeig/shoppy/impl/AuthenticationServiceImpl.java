package ru.chsergeig.shoppy.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.chsergeig.shoppy.component.TokenUtilComponent;
import ru.chsergeig.shoppy.dto.jwt.ResponseDto;
import ru.chsergeig.shoppy.model.JwtUserDetails;
import ru.chsergeig.shoppy.service.AuthenticationService;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final TokenUtilComponent tokenUtilComponent;
    private final UserDetailsService userDetailsService;

    @Override
    public ResponseDto authenticate(String login, String password) {
        Objects.requireNonNull(login, "Given login is null");
        Objects.requireNonNull(password, "Given password is null");
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                login, password
        ));
        UserDetails userDetails = userDetailsService.loadUserByUsername(login);
        if (userDetails instanceof JwtUserDetails) {
            String token = tokenUtilComponent.generateToken((JwtUserDetails) userDetails);
            return new ResponseDto(
                    token,
                    tokenUtilComponent.getExpirationTimeFromToken(token)
            );
        }
        return null;
    }

    @Override
    public Boolean probeToken(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(tokenUtilComponent.getUsernameFromToken(token));
        if (userDetails instanceof JwtUserDetails) {
            return tokenUtilComponent.isTokenValid(token, (JwtUserDetails) userDetails);
        }
        return false;
    }
}
