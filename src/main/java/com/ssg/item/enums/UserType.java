package com.ssg.item.enums;

public enum UserType {
    NORMAL_USER("일반"),
    CORPORATE_USER("기업회원");

    private final String type;

    UserType(String type) {
        this.type = type;
    }
}
