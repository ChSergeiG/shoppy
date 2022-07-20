package ru.chsergeig.shoppy.controller.jwt;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.jooq.tools.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.chsergeig.shoppy.dto.jwt.RequestDto;
import ru.chsergeig.shoppy.dto.jwt.ResponseDto;
import ru.chsergeig.shoppy.properties.SecurityProperties;
import ru.chsergeig.shoppy.service.AuthenticationService;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RequestMapping("/jwt")
@RestController
@CrossOrigin
public class JwtAuthenticationController {

    private final AuthenticationService authenticationService;
    private final SecurityProperties securityProperties;

    @Operation(
            summary = "Do login",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All good"),
                    @ApiResponse(responseCode = "401", description = "Need authorization"),
                    @ApiResponse(responseCode = "500", description = "Error in wrapper")
            }
    )
    @PostMapping(
            value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseDto> doLogin(
            @RequestBody RequestDto requestDto
    ) {
        try {
            return ResponseEntity.ok(
                    authenticationService.authenticate(requestDto.getLogin(), requestDto.getPassword())
            );
        } catch (AuthenticationException ae) {
            if (StringUtils.isBlank(ae.getLocalizedMessage())) {
                throw new InsufficientAuthenticationException("Cant authenticate user", ae);
            }
            throw ae;
        } catch (Exception e) {
            throw new InsufficientAuthenticationException("Cant authenticate user", e);
        }
    }

    @Operation(
            summary = "Check, that authorization header contains valid token",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All good"),
                    @ApiResponse(responseCode = "500", description = "Error in wrapper")
            }
    )
    @GetMapping(
            value = "probe",
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public ResponseEntity<String> probeToken(
            HttpServletRequest httpServletRequest
    ) {
        String token = httpServletRequest.getHeader(securityProperties.getJwt().getAuthorizationHeader());
        try {
            return ResponseEntity.ok(authenticationService.probeToken(token).toString());
        } catch (Exception ignore) {
            return ResponseEntity.ok("false");
        }
    }

    @Operation(
            summary = "Do logout",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All good"),
                    @ApiResponse(responseCode = "500", description = "Error in wrapper")
            }
    )
    @GetMapping(
            value = "logout",
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public ResponseEntity<Void> logout(
            HttpServletRequest httpServletRequest
    ) {
        String token = httpServletRequest.getHeader(securityProperties.getJwt().getAuthorizationHeader());
        authenticationService.logout(token);
        return ResponseEntity.ok(null);
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getLocalizedMessage());
    }

}
