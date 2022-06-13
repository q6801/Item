package com.ssg.item.dto.item;

import com.ssg.item.enums.ItemType;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@EqualsAndHashCode
public class ItemResDto {
    private long id;
    private String name;
    private ItemType itemType;
    private int itemPrice;
    private Timestamp itemDisplayStartDate;
    private Timestamp itemDisplayEndDate;

    public ItemResDto(long id, String name, ItemType itemType, int itemPrice, Timestamp itemDisplayStartDate, Timestamp itemDisplayEndDate) {
        this.id = id;
        this.name = name;
        this.itemType = itemType;
        this.itemPrice = itemPrice;
        this.itemDisplayStartDate = itemDisplayStartDate;
        this.itemDisplayEndDate = itemDisplayEndDate;
    }
}
