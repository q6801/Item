package com.ssg.item.service.mapPromotionItem;

import com.ssg.item.dto.mapPromotionItem.MapPromotionItemDto;

public interface MapPromotionItemService {
    void setPromotionItem(MapPromotionItemDto mapPromotionItemDto);
    void deletePromotionItem(long promotionId, long itemId);
}
