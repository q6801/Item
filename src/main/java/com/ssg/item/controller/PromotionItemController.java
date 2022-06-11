package com.ssg.item.controller;

import com.ssg.item.api.ApiProvider;
import com.ssg.item.api.ApiResult;
import com.ssg.item.dto.PromotionItemDto;
import com.ssg.item.dto.PromotionResDto;
import com.ssg.item.service.PromotionItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1")
@RequiredArgsConstructor
public class PromotionItemController {
    private final PromotionItemService promotionItemService;

    @PostMapping("promotion-item")
    public ApiResult<PromotionResDto> setPromotionItem(@Valid @RequestBody PromotionItemDto promotionItemDto) {
        promotionItemService.setPromotionItem(promotionItemDto);
        return ApiProvider.success();
    }

    @DeleteMapping("promotion-item/promotion/{promotionId}/item/{itemId}")
    public ApiResult<PromotionResDto> setPromotionItem(@PathVariable long promotionId, @PathVariable long itemId) {
        promotionItemService.deletePromotionItem(promotionId, itemId);
        return ApiProvider.success();
    }
}
