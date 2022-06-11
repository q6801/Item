package com.ssg.item.service;

import com.ssg.item.dto.PromotionDto;
import com.ssg.item.dto.PromotionResDto;
import com.ssg.item.entity.Promotion;

public interface PromotionService {
    PromotionResDto setPromotion(PromotionDto promotionDto);
    void deletePromotion(long promotionId);
    Promotion findById(long promotionId);
}
