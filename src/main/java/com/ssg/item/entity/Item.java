package com.ssg.item.entity;

import com.ssg.item.dto.item.ItemDiscountDto;
import com.ssg.item.dto.item.ItemDto;
import com.ssg.item.dto.item.ItemResDto;
import com.ssg.item.enums.ItemType;
import com.ssg.item.exception.CustomRuntimeException;
import com.ssg.item.exception.ExceptionEnum;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, name = "item_name", length = 100)
    private String name;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    @Column(nullable = false)
    private int itemPrice;

    @Column(nullable = false)
    private Timestamp itemDisplayStartDate;

    @Column(nullable = false)
    private Timestamp itemDisplayEndDate;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private List<MapPromotionItem> mapPromotionItems = new ArrayList<>();

    public Item(String name, ItemType itemType, int itemPrice, Timestamp itemDisplayStartDate, Timestamp itemDisplayEndDate) {
        this.name = name;
        this.itemType = itemType;
        this.itemPrice = itemPrice;
        this.itemDisplayStartDate = itemDisplayStartDate;
        this.itemDisplayEndDate = itemDisplayEndDate;

        if (itemDisplayStartDate!=null && itemDisplayEndDate!=null &&
                itemDisplayStartDate.after(itemDisplayEndDate)) {
            throw new CustomRuntimeException(ExceptionEnum.BAD_ITEM_DISPLAY_TIME);
        }
    }

    public Item(long id, String name, ItemType itemType, int itemPrice, Timestamp itemDisplayStartDate, Timestamp itemDisplayEndDate) {
        this(name, itemType, itemPrice, itemDisplayStartDate, itemDisplayEndDate);
        this.id = id;
    }

    public static Item convertDtoToItem(ItemDto itemDto) {
        return new Item(itemDto.getName(), itemDto.getItemType(), itemDto.getItemPrice(),
                itemDto.getItemDisplayStartDate(), itemDto.getItemDisplayEndDate());
    }

    public ItemResDto convertItemToResDto() {
        return new ItemResDto(this.id, this.name, this.itemType, this.itemPrice,
                this.itemDisplayStartDate, this.itemDisplayEndDate);
    }

    public ItemDiscountDto convertItemToDiscountDto(int discountedPrice) {
        return new ItemDiscountDto(this.id, this.name, this.itemType, this.itemPrice,
                discountedPrice, this.itemDisplayStartDate, this.itemDisplayEndDate);
    }
}
