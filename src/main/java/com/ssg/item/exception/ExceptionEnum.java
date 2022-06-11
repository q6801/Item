package com.ssg.item.exception;

import lombok.Getter;

@Getter
public enum ExceptionEnum {
    USER_NOT_FOUND("user-0001", "해당하는 유저가 없다."),
    USER_TYPE_NOT_FOUND("user-0002", "잘못된 유저 타입이다."),
    USER_STAT_NOT_FOUND("user-0003", "잘못된 유저 스탯이다."),

    ARGUMENT_NOT_VALID("common-0001", ""),

    ITEM_NOT_FOUND("item-0001", "해당하는 상품이 없다."),
    BAD_ITEM_DISPLAY_TIME("item-0002", "상품 전시 시작 날이 종료 일보다 늦다."),
    ITEM_TYPE_NOT_FOUND("item-0003", "잘못된 상품 타입이다."),

    BAD_PROMOTION_TIME("promotion-0001", "프로모션 시작 날이 종료 일보다 늦다."),
    PROMOTION_NOT_FOUND("promotion-0002", "해당하는 프로모션이 없다."),
    DISCOUNT_NOT_FOUND("promotion-0003", "할인 비율과 할인 비용 둘 다 널값이면 안된다.");

    private String code;
    private String message;

    ExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
