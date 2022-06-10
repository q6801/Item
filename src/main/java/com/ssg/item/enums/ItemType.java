package com.ssg.item.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.ssg.item.exception.CustomRuntimeException;
import com.ssg.item.exception.ExceptionEnum;

import java.util.Arrays;

public enum ItemType {
    NORMAL_ITEM("일반"),
    CORPORATE_USER_ITEM("기업회원상품");

    private final String type;

    ItemType(String type) {
        this.type = type;
    }

    @JsonCreator
    public static ItemType of(String type) {
        return Arrays.stream(ItemType.values())
                .filter(i -> i.getType().equals(type)).findAny()
                .orElseThrow(() -> new CustomRuntimeException(ExceptionEnum.USER_STAT_NOT_FOUND));
    }

    @JsonValue
    public String getType() {
        return type;
    }
}
