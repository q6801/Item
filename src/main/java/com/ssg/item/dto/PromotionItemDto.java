package com.ssg.item.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class PromotionItemDto {
    private long promotionId;

    @NotBlank(message = "item id들은 빈 값이어서는 안됩니다.")
    private String itemIds;

    public PromotionItemDto(long promotionId, String itemIds) {
        this.promotionId = promotionId;
        this.itemIds = itemIds;
    }
}
