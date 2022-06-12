package com.ssg.item.service;

import com.ssg.item.dto.MapPromotionItemDto;
import com.ssg.item.entity.Item;
import com.ssg.item.entity.Promotion;
import com.ssg.item.entity.MapPromotionItem;
import com.ssg.item.exception.CustomRuntimeException;
import com.ssg.item.exception.ExceptionEnum;
import com.ssg.item.repository.ItemRepository;
import com.ssg.item.repository.MapPromotionItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MapPromotionItemServiceImpl implements MapPromotionItemService {
    private final MapPromotionItemRepository mapPromotionItemRepository;
    private final PromotionService promotionService;
    private final ItemRepository itemRepository;
    private final ItemService itemService;

    @Override
    @Transactional
    public void setPromotionItem(MapPromotionItemDto mapPromotionItemDto) {
        long promotionId = mapPromotionItemDto.getPromotionId();
        String[] itemId = mapPromotionItemDto.getItemIds().split(",");
        Promotion promotion = promotionService.findById(promotionId);
        
        for(String id : itemId) {
            isNumeric(id);
            Item item = findItemById(id);
            if(mapPromotionItemRepository.findByItemAndPromotion(item, promotion).isPresent()) continue;   // 중복 시 제거
            MapPromotionItem mapPromotionItem = setPromotionItem(promotion, item);
            mapPromotionItemRepository.save(mapPromotionItem);
        }
    }

    private MapPromotionItem setPromotionItem(Promotion promotion, Item item) {
        MapPromotionItem mapPromotionItem = new MapPromotionItem();
        mapPromotionItem.setPromotion(promotion);
        mapPromotionItem.setItem(item);
        return mapPromotionItem;
    }

    private Item findItemById(String id) {
        ExceptionEnum itemNotFound = ExceptionEnum.ITEM_NOT_FOUND;
        return itemRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new CustomRuntimeException(
                        itemNotFound.getCode(), itemNotFound.getMessage() + " num: " + id));
    }

    private void isNumeric(String num) {
        try {
            Long.parseLong(num);
        } catch(NumberFormatException e) {
            ExceptionEnum argumentNotValid = ExceptionEnum.ARGUMENT_NOT_VALID;
            throw new CustomRuntimeException(argumentNotValid.getCode(), num + "은(는) 숫자가 아니다.");
        }
    }

    @Override
    @Transactional
    public void deletePromotionItem(long promotionId, long itemId) {
        Promotion promotion = promotionService.findById(promotionId);
        Item item = itemService.findById(itemId);
        mapPromotionItemRepository.deleteByItemAndPromotion(item, promotion);
    }
}
