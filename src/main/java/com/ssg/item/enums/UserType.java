package com.ssg.item.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.ssg.item.exception.CustomRuntimeException;
import com.ssg.item.exception.ExceptionEnum;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum UserType {
    NORMAL_USER("일반"),
    CORPORATE_USER("기업회원");

    private final String type;

    UserType(String type) {
        this.type = type;
    }

    @JsonCreator
    public static UserType of(String type) {
        return Arrays.stream(UserType.values())
                .filter(u -> u.getType().equals(type)).findAny()
                .orElseThrow(() -> new CustomRuntimeException(ExceptionEnum.USER_TYPE_NOT_FOUND));
    }

    @JsonValue
    public String getType() {
        return type;
    }
}
