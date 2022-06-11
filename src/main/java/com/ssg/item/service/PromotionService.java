package com.ssg.item.service;

import com.ssg.item.dto.PromotionDto;
import com.ssg.item.dto.PromotionResDto;

public interface PromotionService {
    PromotionResDto setPromotion(PromotionDto promotionDto);
    void deletePromotion(long promotionId);
}
