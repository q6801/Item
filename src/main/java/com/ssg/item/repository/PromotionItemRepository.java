package com.ssg.item.repository;

import com.ssg.item.entity.Item;
import com.ssg.item.entity.Promotion;
import com.ssg.item.entity.PromotionItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PromotionItemRepository extends JpaRepository<PromotionItem, Long> {
    Optional<PromotionItem> findByItemAndPromotion(Item item, Promotion promotion);
    void deleteByItemAndPromotion(Item item, Promotion promotion);
}
