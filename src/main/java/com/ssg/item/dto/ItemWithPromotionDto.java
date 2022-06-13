package com.ssg.item.dto;

import lombok.Getter;

@Getter
public class ItemWithPromotionDto {
    ItemDiscountDto itemDiscountDto;
    PromotionResDto promotionResDto;

    public ItemWithPromotionDto(ItemDiscountDto itemDiscountDto, PromotionResDto promotionResDto) {
        this.itemDiscountDto = itemDiscountDto;
        this.promotionResDto = promotionResDto;
    }
}
