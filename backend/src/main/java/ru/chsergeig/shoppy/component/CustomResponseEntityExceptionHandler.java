package ru.chsergeig.shoppy.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.chsergeig.shoppy.exception.ControllerException;
import ru.chsergeig.shoppy.exception.ServiceException;

@Slf4j
@RestControllerAdvice
public class CustomResponseEntityExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String internalServerError(Exception ex) {
        return ex.getLocalizedMessage();
    }

    @ExceptionHandler(value = {ControllerException.class})
    public ResponseEntity<String> controllerException(ControllerException ex) {
        if (ex.code == null || ex.code == 499) {
            return ResponseEntity
                    .status(499)
                    .body(ex.reason);
        } else {
            return ResponseEntity
                    .status(ex.code)
                    .body(ex.reason);
        }
    }

    @ExceptionHandler(value = {ServiceException.class})
    public ResponseEntity<String> serviceException(ServiceException ex) {
        return ResponseEntity
                .status(499)
                .body(ex.getMessage());
    }

}
