package com.ssg.item.service;

import com.ssg.item.dto.ItemDto;
import com.ssg.item.dto.ItemResDto;

public interface ItemService {
    ItemResDto setItem(ItemDto itemDto);
    void deleteItem(long itemId);
}