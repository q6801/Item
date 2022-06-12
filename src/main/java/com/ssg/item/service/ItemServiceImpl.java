package com.ssg.item.service;

import com.ssg.item.dto.ItemDto;
import com.ssg.item.dto.ItemResDto;
import com.ssg.item.entity.Item;
import com.ssg.item.entity.User;
import com.ssg.item.enums.ItemType;
import com.ssg.item.enums.UserStat;
import com.ssg.item.enums.UserType;
import com.ssg.item.exception.CustomRuntimeException;
import com.ssg.item.exception.ExceptionEnum;
import com.ssg.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{
    private final ItemRepository itemRepository;
    private final UserService userService;

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

    @Override
    public List<ItemResDto> getBuyableItem(long userId) {
        User user = userService.findById(userId);
        if (user.getUserStat().equals(UserStat.WITHDRAWAL)) {
            return new ArrayList<>();
        }

        List<Item> buyableItems;
        if (user.getUserType().equals(UserType.NORMAL_USER)) {
            buyableItems = itemRepository.findBuyableItem(ItemType.NORMAL_ITEM);
        } else {
            buyableItems = itemRepository.findBuyableItem();
        }

        return buyableItems.stream()
                .map(Item::convertItemToResDto).collect(Collectors.toList());
    }
}
