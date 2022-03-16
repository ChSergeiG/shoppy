package ru.chsergeig.shoppy.exception;

public class ControllerException extends RuntimeException {

    public Integer code;
    public String reason;

    public ControllerException(int rawStatusCode, String reason, Throwable cause) {
        super(cause);
        this.code = rawStatusCode;
        this.reason = reason;
    }

}
