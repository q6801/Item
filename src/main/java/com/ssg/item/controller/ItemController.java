package com.ssg.item.controller;

import com.ssg.item.api.ApiProvider;
import com.ssg.item.api.ApiResult;
import com.ssg.item.dto.item.ItemDto;
import com.ssg.item.dto.item.ItemResDto;
import com.ssg.item.dto.item.ItemWithPromotionDto;
import com.ssg.item.service.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("v1")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("buyable-item/{userId}")
    public ApiResult<List<ItemResDto>> getBuyalbeItem(@PathVariable long userId) {
        List<ItemResDto> buyableItem = itemService.getBuyableItem(userId);
        return ApiProvider.success(buyableItem);
    }

    @GetMapping("item-promotion/{itemId}")
    public ApiResult<ItemWithPromotionDto> getItemWithPromotion(@PathVariable long itemId) {
        ItemWithPromotionDto promotionInItem = itemService.getItemWithPromotion(itemId);
        return ApiProvider.success(promotionInItem);

    }

    @PostMapping("item")
    public ApiResult<ItemResDto> setItem(@Valid @RequestBody ItemDto itemDto) {
        ItemResDto itemResDto = itemService.setItem(itemDto);
        return ApiProvider.success(itemResDto);
    }

    @DeleteMapping("item/{itemId}")
    public ApiResult<?> deleteItem(@PathVariable long itemId) {
        itemService.deleteItem(itemId);
        return ApiProvider.success();
    }
}
