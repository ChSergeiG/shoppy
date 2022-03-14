package ru.chsergeig.shoppy.controller.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.chsergeig.shoppy.dto.jwt.RequestDto;
import ru.chsergeig.shoppy.dto.jwt.ResponseDto;
import ru.chsergeig.shoppy.service.AuthenticationService;

@RequiredArgsConstructor
@RequestMapping("/jwt")
@RestController
public class JwtAuthenticationController {

    private final AuthenticationService authenticationService;

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

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getLocalizedMessage());
    }

}
