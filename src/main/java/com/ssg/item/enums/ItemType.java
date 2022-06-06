package com.ssg.item.enums;

public enum ItemType {
    NORMAL_ITEM("일반"),
    CORPORATE_USER_ITEM("기업회원상품");

    private final String type;

    ItemType(String type) {
        this.type = type;
    }
}
