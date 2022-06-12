package com.ssg.item.service;

import com.ssg.item.dto.MapPromotionItemDto;

public interface MapPromotionItemService {
    void setPromotionItem(MapPromotionItemDto mapPromotionItemDto);
    void deletePromotionItem(long promotionId, long itemId);
}
