package com.ssg.item.controller;

import com.ssg.item.api.ApiProvider;
import com.ssg.item.api.ApiResult;
import com.ssg.item.dto.promotion.PromotionDto;
import com.ssg.item.dto.promotion.PromotionResDto;
import com.ssg.item.service.promotion.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1")
public class PromotionController {
    private final PromotionService promotionService;

    @PostMapping("promotion")
    public ApiResult<PromotionResDto> setPromotion(@Valid @RequestBody PromotionDto promotionDto) {
        PromotionResDto promotionResDto = promotionService.setPromotion(promotionDto);
        return ApiProvider.success(promotionResDto);
    }

    @DeleteMapping("promotion/{promotionId}")
    public ApiResult<?> deletePromotion(@PathVariable long promotionId) {
        promotionService.deletePromotion(promotionId);
        return ApiProvider.success();
    }
}
