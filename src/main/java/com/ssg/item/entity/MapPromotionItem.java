package com.ssg.item.entity;

import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "map_promotions_items")
public class MapPromotionItem {
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

    public void setItem(Item item) {
        if(this.item != null) {
            this.item.getMapPromotionItems().remove(this);
        }
        this.item = item;
        item.getMapPromotionItems().add(this);
    }

    public void setPromotion(Promotion promotion) {
        if(this.promotion != null) {
            this.promotion.getMapPromotionItems().remove(this);
        }
        this.promotion = promotion;
        promotion.getMapPromotionItems().add(this);
    }
}
