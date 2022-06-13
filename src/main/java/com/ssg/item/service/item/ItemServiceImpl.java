package com.ssg.item.service.item;

import com.ssg.item.dto.item.ItemDiscountDto;
import com.ssg.item.dto.item.ItemDto;
import com.ssg.item.dto.item.ItemResDto;
import com.ssg.item.dto.item.ItemWithPromotionDto;
import com.ssg.item.dto.promotion.PromotionResDto;
import com.ssg.item.entity.Item;
import com.ssg.item.entity.Promotion;
import com.ssg.item.entity.MapPromotionItem;
import com.ssg.item.entity.User;
import com.ssg.item.enums.ItemType;
import com.ssg.item.enums.UserStat;
import com.ssg.item.enums.UserType;
import com.ssg.item.exception.CustomRuntimeException;
import com.ssg.item.exception.ExceptionEnum;
import com.ssg.item.repository.ItemRepository;
import com.ssg.item.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
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

    @Override
    public ItemWithPromotionDto getItemWithPromotion(long itemId) {
        Item item = findById(itemId);
        int itemPrice = item.getItemPrice();
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        List<MapPromotionItem> mapPromotionItems = item.getMapPromotionItems();
        List<PromotionResDto> promotionResDtos = mapPromotionItems.stream()
                .map(MapPromotionItem::getPromotion)
                .map(Promotion::convertPromotionToResDto)
                .filter(p -> p.getPromotionStartDate().before(now))
                .filter(p -> p.getPromotionEndDate().after(now))
                .collect(Collectors.toList());
        int discountedPrice = itemPrice;
        PromotionResDto selectedPromotion = null;

        for(PromotionResDto dto : promotionResDtos) {
            int discountAmount = dto.getDiscountAmount() != null ? dto.getDiscountAmount(): 0;
            float discountRate = dto.getDiscountRate() != null ? dto.getDiscountRate(): 0;
            int price0 = itemPrice - discountAmount;
            int price1 = (int) (itemPrice * (1 - discountRate));
            price0 = price0 > 0 ? price0 : itemPrice + 1;
            price1 = price1 > 0 ? price1 : itemPrice + 1;

            if (Math.min(price0, price1) < discountedPrice) {
                discountedPrice = Math.min(price0, price1);
                selectedPromotion = dto;
            }
        }
        ItemDiscountDto itemDiscountDto = item.convertItemToDiscountDto(discountedPrice);
        return new ItemWithPromotionDto(itemDiscountDto, selectedPromotion);
    }
}
