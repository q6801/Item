package com.ssg.item.dto.item;

import com.ssg.item.dto.promotion.PromotionResDto;
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
