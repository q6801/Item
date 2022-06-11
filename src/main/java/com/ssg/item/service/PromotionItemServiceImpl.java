package com.ssg.item.service;

import com.ssg.item.dto.PromotionItemDto;
import com.ssg.item.entity.Item;
import com.ssg.item.entity.Promotion;
import com.ssg.item.entity.PromotionItem;
import com.ssg.item.exception.CustomRuntimeException;
import com.ssg.item.exception.ExceptionEnum;
import com.ssg.item.repository.ItemRepository;
import com.ssg.item.repository.PromotionItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PromotionItemServiceImpl implements PromotionItemService {
    private final PromotionItemRepository promotionItemRepository;
    private final PromotionService promotionService;
    private final ItemRepository itemRepository;
    private final ItemService itemService;

    @Override
    @Transactional
    public void setPromotionItem(PromotionItemDto promotionItemDto) {
        long promotionId = promotionItemDto.getPromotionId();
        String[] itemId = promotionItemDto.getItemIds().split(",");
        Promotion promotion = promotionService.findById(promotionId);
        
        for(String id : itemId) {
            isNumeric(id);
            Item item = findItemById(id);
            if(promotionItemRepository.findByItemAndPromotion(item, promotion).isPresent()) continue;   // 중복 시 제거
            PromotionItem promotionItem = setPromotionItem(promotion, item);
            promotionItemRepository.save(promotionItem);
        }
    }

    private PromotionItem setPromotionItem(Promotion promotion, Item item) {
        PromotionItem promotionItem = new PromotionItem();
        promotionItem.setPromotion(promotion);
        promotionItem.setItem(item);
        return promotionItem;
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
        promotionItemRepository.deleteByItemAndPromotion(item, promotion);
    }
}
