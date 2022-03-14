package ru.chsergeig.shoppy.controller.jwt;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        return ResponseEntity.ok(
                authenticationService.authenticate(requestDto.getLogin(), requestDto.getPassword())
        );
    }

    @Operation(
            summary = "Check, that authorization header contains valid token",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All good"),
                    @ApiResponse(responseCode = "500", description = "Error in wrapper")
            }
    )
    @GetMapping("probe")
    public ResponseEntity<Boolean> probeToken(
            HttpServletRequest httpServletRequest
    ) {
        String token = httpServletRequest.getHeader(securityProperties.getJwt().getAuthorizationHeader());
        try {
            return ResponseEntity.ok(authenticationService.probeToken(token));
        } catch (Exception ignore) {
            return ResponseEntity.ok(false);
        }
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getLocalizedMessage());
    }

}
