package com.ssg.item.service.item;

import com.ssg.item.dto.item.ItemDto;
import com.ssg.item.dto.item.ItemResDto;
import com.ssg.item.dto.item.ItemWithPromotionDto;
import com.ssg.item.entity.Item;

import java.util.List;

public interface ItemService {
    ItemResDto setItem(ItemDto itemDto);
    void deleteItem(long itemId);
    Item findById(long itemId);
    List<ItemResDto> getBuyableItem(long userId);
    ItemWithPromotionDto getItemWithPromotion(long itemId);
}
