package com.ssg.item.exception;

import lombok.Getter;

@Getter
public enum ExceptionEnum {
    USER_NOT_FOUND("user-0001", "해당하는 유저가 없다.");

    private String code;
    private String message;

    ExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
