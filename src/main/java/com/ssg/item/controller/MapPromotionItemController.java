package com.ssg.item.controller;

import com.ssg.item.api.ApiProvider;
import com.ssg.item.api.ApiResult;
import com.ssg.item.dto.MapPromotionItemDto;
import com.ssg.item.dto.PromotionResDto;
import com.ssg.item.service.MapPromotionItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1")
@RequiredArgsConstructor
public class MapPromotionItemController {
    private final MapPromotionItemService mapPromotionItemService;

    @PostMapping("promotion-item")
    public ApiResult<PromotionResDto> setPromotionItem(@Valid @RequestBody MapPromotionItemDto mapPromotionItemDto) {
        mapPromotionItemService.setPromotionItem(mapPromotionItemDto);
        return ApiProvider.success();
    }

    @DeleteMapping("promotion-item/promotion/{promotionId}/item/{itemId}")
    public ApiResult<PromotionResDto> setPromotionItem(@PathVariable long promotionId, @PathVariable long itemId) {
        mapPromotionItemService.deletePromotionItem(promotionId, itemId);
        return ApiProvider.success();
    }
}
