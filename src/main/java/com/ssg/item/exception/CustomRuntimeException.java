package com.ssg.item.exception;

import lombok.Getter;

@Getter
public class CustomRuntimeException extends RuntimeException {
    private ExceptionEnum exceptionEnum;

    public CustomRuntimeException(ExceptionEnum exceptionEnum) {
        this.exceptionEnum = exceptionEnum;
    }
}
