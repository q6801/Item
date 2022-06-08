package com.ssg.item.dto;

import com.ssg.item.enums.ItemType;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
public class ItemDto {
    @NotBlank
    private String name;
    @NotNull
    private ItemType itemType;
    private int itemPrice;
    @NotNull
    private Timestamp itemDisplayStartDate;
    @NotNull
    private Timestamp itemDisplayEndDate;
}
