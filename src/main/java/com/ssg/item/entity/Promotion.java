package com.ssg.item.entity;

import com.ssg.item.dto.promotion.PromotionDto;
import com.ssg.item.dto.promotion.PromotionResDto;
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
@Table(name = "promotions")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Promotion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, name = "promotion_nm", length=100)
    private String name;

    private Integer discountAmount;

    private Float discountRate;

    @Column(nullable = false)
    private Timestamp promotionStartDate;

    @Column(nullable = false)
    private Timestamp promotionEndDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "promotion")
    private List<MapPromotionItem> mapPromotionItems = new ArrayList<>();

    public Promotion(String name, Integer discountAmount, Float discountRate,
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

    public Promotion(long id, String name, Integer discountAmount, Float discountRate,
                     Timestamp promotionStartDate, Timestamp promotionEndDate) {
        this(name, discountAmount, discountRate, promotionStartDate, promotionEndDate);
        this.id = id;
    }

    public static Promotion convertDtoToPromotion(PromotionDto promotionDto) {
        return new Promotion(promotionDto.getName(), promotionDto.getDiscountAmount(), promotionDto.getDiscountRate(),
                promotionDto.getPromotionStartDate(), promotionDto.getPromotionEndDate());
    }

    public PromotionResDto convertPromotionToResDto() {
        return new PromotionResDto(this.id, this.name, this.discountAmount, this.discountRate,
                this.promotionStartDate, this.promotionEndDate);
    }
}
