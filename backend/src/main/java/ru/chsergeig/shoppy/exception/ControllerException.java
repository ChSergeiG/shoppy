package ru.chsergeig.shoppy.exception;

import org.springframework.http.HttpStatus;

public class ControllerException extends RuntimeException {

    public Integer code;
    public String reason;

    public ControllerException(int statusCode, String reason, Throwable cause) {
        super(cause);
        this.code = statusCode;
        this.reason = reason;
    }

    public ControllerException(HttpStatus desiredStatus, String reason, Throwable cause) {
        this(desiredStatus.value(), reason, cause);
    }

}
