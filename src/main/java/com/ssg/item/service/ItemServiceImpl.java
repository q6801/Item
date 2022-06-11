package com.ssg.item.service;

import com.ssg.item.dto.ItemDto;
import com.ssg.item.dto.ItemResDto;
import com.ssg.item.entity.Item;
import com.ssg.item.exception.CustomRuntimeException;
import com.ssg.item.exception.ExceptionEnum;
import com.ssg.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{
    private final ItemRepository itemRepository;

    @Override
    @Transactional
    public ItemResDto setItem(ItemDto itemDto) {
        Item item = Item.convertDtoToItem(itemDto);
        return itemRepository.save(item).convertItemToResDto();
    }

    @Override
    @Transactional
    public void deleteItem(long itemId) {
        Item item = findById(itemId);
        itemRepository.delete(item);
    }

    @Override
    public Item findById(long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new CustomRuntimeException(ExceptionEnum.ITEM_NOT_FOUND));
    }
}
