package com.ssg.item.dto;

import com.ssg.item.enums.ItemType;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@EqualsAndHashCode
public class ItemDiscountDto {
    private long id;
    private String name;
    private ItemType itemType;
    private int itemPrice;
    private int discountedPrice;
    private Timestamp itemDisplayStartDate;
    private Timestamp itemDisplayEndDate;

    public ItemDiscountDto(long id, String name, ItemType itemType, int itemPrice, int discountedPrice, Timestamp itemDisplayStartDate, Timestamp itemDisplayEndDate) {
        this.id = id;
        this.name = name;
        this.itemType = itemType;
        this.itemPrice = itemPrice;
        this.discountedPrice = discountedPrice;
        this.itemDisplayStartDate = itemDisplayStartDate;
        this.itemDisplayEndDate = itemDisplayEndDate;
    }
}
