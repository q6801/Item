package com.ssg.item.dto;

import lombok.Getter;

import java.sql.Timestamp;

@Getter
public class PromotionResDto {
    private long id;
    private String name;
    private Integer discountAmount;
    private Float discountRate;
    private Timestamp promotionStartDate;
    private Timestamp promotionEndDate;

    public PromotionResDto(String name, Integer discountAmount, Float discountRate,
                        Timestamp promotionStartDate, Timestamp promotionEndDate) {
        this.name = name;
        this.discountAmount = discountAmount;
        this.discountRate = discountRate;
        this.promotionStartDate = promotionStartDate;
        this.promotionEndDate = promotionEndDate;
    }
}
