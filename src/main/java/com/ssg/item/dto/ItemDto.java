package com.ssg.item.dto;

import com.ssg.item.enums.ItemType;
import com.ssg.item.exception.CustomRuntimeException;
import com.ssg.item.exception.ExceptionEnum;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
public class ItemDto {
    @NotBlank(message = "상품의 이름은 빈값일 수 없습니다.")
    private String name;
    @NotNull(message = "상품의 타입은 널값일 수 없습니다.")
    private ItemType itemType;
    @Min(value = 0, message = "상품의 비용은 음수일 수 없습니다.")
    private int itemPrice;
    @NotNull(message = "상품 전시 시작기간은 널값일 수 없습니다.")
    private Timestamp itemDisplayStartDate;
    @NotNull(message = "상품 전시 종료기간은 널값일 수 없습니다.")
    private Timestamp itemDisplayEndDate;

    public ItemDto(String name, ItemType itemType, int itemPrice, Timestamp itemDisplayStartDate, Timestamp itemDisplayEndDate) {
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
}
