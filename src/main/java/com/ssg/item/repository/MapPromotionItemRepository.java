package com.ssg.item.repository;

import com.ssg.item.entity.Item;
import com.ssg.item.entity.Promotion;
import com.ssg.item.entity.MapPromotionItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MapPromotionItemRepository extends JpaRepository<MapPromotionItem, Long> {
    Optional<MapPromotionItem> findByItemAndPromotion(Item item, Promotion promotion);
    void deleteByItemAndPromotion(Item item, Promotion promotion);
}
