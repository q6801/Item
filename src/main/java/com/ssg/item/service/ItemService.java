package com.ssg.item.service;

import com.ssg.item.dto.ItemDto;
import com.ssg.item.dto.ItemResDto;
import com.ssg.item.entity.Item;

import java.util.List;

public interface ItemService {
    ItemResDto setItem(ItemDto itemDto);
    void deleteItem(long itemId);
    Item findById(long itemId);
    List<ItemResDto> getBuyableItem(long userId);
}
