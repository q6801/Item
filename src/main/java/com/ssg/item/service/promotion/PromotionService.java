package com.ssg.item.service.promotion;

import com.ssg.item.dto.promotion.PromotionDto;
import com.ssg.item.dto.promotion.PromotionResDto;
import com.ssg.item.entity.Promotion;

public interface PromotionService {
    PromotionResDto setPromotion(PromotionDto promotionDto);
    void deletePromotion(long promotionId);
    Promotion findById(long promotionId);
}
