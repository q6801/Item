package com.ssg.item.service.promotion;

import com.ssg.item.dto.promotion.PromotionDto;
import com.ssg.item.dto.promotion.PromotionResDto;
import com.ssg.item.entity.Promotion;
import com.ssg.item.exception.CustomRuntimeException;
import com.ssg.item.exception.ExceptionEnum;
import com.ssg.item.repository.PromotionRepository;
import com.ssg.item.service.promotion.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {
    private final PromotionRepository promotionRepository;

    @Override
    public PromotionResDto setPromotion(PromotionDto promotionDto) {
        Promotion promotion = Promotion.convertDtoToPromotion(promotionDto);
        return promotionRepository.save(promotion).convertPromotionToResDto();
    }

    @Override
    public void deletePromotion(long promotionId) {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new CustomRuntimeException(ExceptionEnum.PROMOTION_NOT_FOUND));
        promotionRepository.delete(promotion);
    }

    @Override
    public Promotion findById(long promotionId) {
        return promotionRepository.findById(promotionId)
                .orElseThrow(() -> new CustomRuntimeException(ExceptionEnum.PROMOTION_NOT_FOUND));
    }
}
