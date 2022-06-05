package com.ssg.item.entity;

import com.ssg.item.enums.ItemType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private List<PromotionItem> promotionItems;
}
