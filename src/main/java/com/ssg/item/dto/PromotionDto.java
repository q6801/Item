package com.ssg.item.dto;

import com.ssg.item.exception.CustomRuntimeException;
import com.ssg.item.exception.ExceptionEnum;
import lombok.Getter;

import javax.validation.constraints.*;
import java.sql.Timestamp;

@Getter
public class PromotionDto {
    @NotBlank(message = "프로모션의 이름은 빈값일 수 없습니다.")
    private String name;

    @Min(value = 1, message = "할인 비용은 0보다 커야합니다.")
    private Integer discountAmount;

    @DecimalMax(value = "1.0", inclusive = false, message = "할인 비율은 1 미만입니다.")
    @DecimalMin(value = "0.0", inclusive = false, message = "할인 비율은 0 초과입니다.")
    private Float discountRate;

    @NotNull(message = "상품 전시 시작기간은 널값일 수 없습니다.")
    private Timestamp promotionStartDate;

    @NotNull(message = "상품 전시 종료기간은 널값일 수 없습니다.")
    private Timestamp promotionEndDate;

    public PromotionDto(String name, Integer discountAmount, Float discountRate,
                        Timestamp promotionStartDate, Timestamp promotionEndDate) {
        this.name = name;
        this.discountAmount = discountAmount;
        this.discountRate = discountRate;
        this.promotionStartDate = promotionStartDate;
        this.promotionEndDate = promotionEndDate;

        if (promotionStartDate!=null && promotionEndDate!=null &&
                promotionStartDate.after(promotionEndDate)) {
            throw new CustomRuntimeException(ExceptionEnum.BAD_PROMOTION_TIME);
        }

        if (discountAmount == null && discountRate == null) {
            throw new CustomRuntimeException(ExceptionEnum.DISCOUNT_NOT_FOUND);
        }
    }
}
