package com.ssg.item.service;

import com.ssg.item.dto.PromotionItemDto;

public interface PromotionItemService {
    void setPromotionItem(PromotionItemDto promotionItemDto);
    void deletePromotionItem(long promotionId, long itemId);
}
