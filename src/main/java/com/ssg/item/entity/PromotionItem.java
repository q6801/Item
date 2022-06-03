package com.ssg.item.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "map_promotions_items")
public class PromotionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id", foreignKey = @ForeignKey(name = "fk_promotion_item"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="promotion_id", foreignKey = @ForeignKey(name = "fk_item_promotion"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Promotion promotion;
}
