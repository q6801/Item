package com.ssg.item.exception;

import lombok.Getter;

@Getter
public class CustomRuntimeException extends RuntimeException {
    private String code;
    private String message;

    public CustomRuntimeException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public CustomRuntimeException(ExceptionEnum exceptionEnum) {
        this.code = exceptionEnum.getCode();
        this.message = exceptionEnum.getMessage();
    }
}
