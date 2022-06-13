package com.ssg.item.repository;

import com.ssg.item.entity.Item;
import com.ssg.item.enums.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("select i from Item i " +
            "where i.itemType=:itemType " +
            "and i.itemDisplayStartDate <= CURRENT_TIMESTAMP " +
            "and i.itemDisplayEndDate >= CURRENT_TIMESTAMP ")
    List<Item> findBuyableItem(@Param("itemType") ItemType itemType);
    @Query("select i from Item i " +
            "where i.itemDisplayStartDate <= CURRENT_TIMESTAMP " +
            "and i.itemDisplayEndDate >= CURRENT_TIMESTAMP ")
    List<Item> findBuyableItem();
}
