package com.retail.offersviewer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "offers", indexes = {
    @Index(name = "idx_store_id", columnList = "store_id"),
    @Index(name = "idx_category", columnList = "category"),
    @Index(name = "idx_valid_until", columnList = "valid_until")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "store_id", nullable = false)
    private String storeId;

    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false)
    private String category;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", nullable = false)
    private DiscountType discountType;

    @Column(name = "discount_value", nullable = false)
    private Double discountValue;

    @Column(name = "original_price")
    private Double originalPrice;

    @Column(name = "final_price")
    private Double finalPrice;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "valid_from", nullable = false)
    private LocalDateTime validFrom;

    @Column(name = "valid_until", nullable = false)
    private LocalDateTime validUntil;

    @ElementCollection
    @CollectionTable(name = "offer_terms", joinColumns = @JoinColumn(name = "offer_id"))
    @Column(name = "term")
    private List<String> terms = new ArrayList<>();

    @Column(name = "requires_loyalty_card")
    private Boolean requiresLoyaltyCard;

    @Column(name = "coupon_code")
    private String couponCode;

    @Column(name = "minimum_purchase")
    private Double minimumPurchase;

    @ElementCollection
    @CollectionTable(name = "offer_eligible_products", joinColumns = @JoinColumn(name = "offer_id"))
    @Column(name = "product")
    private List<String> eligibleProducts = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "offer_exclusions", joinColumns = @JoinColumn(name = "offer_id"))
    @Column(name = "exclusion")
    private List<String> exclusions = new ArrayList<>();

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public enum DiscountType {
        PERCENTAGE, FIXED, BOGO, BUNDLE
    }
}
