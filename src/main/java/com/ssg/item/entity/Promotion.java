package com.ssg.item.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "promotions")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private List<PromotionItem> promotionItems;
}
