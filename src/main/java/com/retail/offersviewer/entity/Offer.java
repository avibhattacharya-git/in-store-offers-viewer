package com.retail.offersviewer.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "offers", indexes = {
    @Index(name = "idx_store_id", columnList = "store_id"),
    @Index(name = "idx_category", columnList = "category"),
    @Index(name = "idx_valid_until", columnList = "valid_until")
})
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
    
    // Constructors
    public Offer() {
    }
    
    public Offer(String id, String storeId, String title, String description, String category,
                 DiscountType discountType, Double discountValue, Double originalPrice, Double finalPrice,
                 String imageUrl, LocalDateTime validFrom, LocalDateTime validUntil, List<String> terms,
                 Boolean requiresLoyaltyCard, String couponCode, Double minimumPurchase,
                 List<String> eligibleProducts, List<String> exclusions, LocalDateTime createdAt) {
        this.id = id;
        this.storeId = storeId;
        this.title = title;
        this.description = description;
        this.category = category;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.originalPrice = originalPrice;
        this.finalPrice = finalPrice;
        this.imageUrl = imageUrl;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
        this.terms = terms;
        this.requiresLoyaltyCard = requiresLoyaltyCard;
        this.couponCode = couponCode;
        this.minimumPurchase = minimumPurchase;
        this.eligibleProducts = eligibleProducts;
        this.exclusions = exclusions;
        this.createdAt = createdAt;
    }
    
    // Getters
    public String getId() { return id; }
    public String getStoreId() { return storeId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public DiscountType getDiscountType() { return discountType; }
    public Double getDiscountValue() { return discountValue; }
    public Double getOriginalPrice() { return originalPrice; }
    public Double getFinalPrice() { return finalPrice; }
    public String getImageUrl() { return imageUrl; }
    public LocalDateTime getValidFrom() { return validFrom; }
    public LocalDateTime getValidUntil() { return validUntil; }
    public List<String> getTerms() { return terms; }
    public Boolean getRequiresLoyaltyCard() { return requiresLoyaltyCard; }
    public String getCouponCode() { return couponCode; }
    public Double getMinimumPurchase() { return minimumPurchase; }
    public List<String> getEligibleProducts() { return eligibleProducts; }
    public List<String> getExclusions() { return exclusions; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    
    // Setters
    public void setId(String id) { this.id = id; }
    public void setStoreId(String storeId) { this.storeId = storeId; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setCategory(String category) { this.category = category; }
    public void setDiscountType(DiscountType discountType) { this.discountType = discountType; }
    public void setDiscountValue(Double discountValue) { this.discountValue = discountValue; }
    public void setOriginalPrice(Double originalPrice) { this.originalPrice = originalPrice; }
    public void setFinalPrice(Double finalPrice) { this.finalPrice = finalPrice; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setValidFrom(LocalDateTime validFrom) { this.validFrom = validFrom; }
    public void setValidUntil(LocalDateTime validUntil) { this.validUntil = validUntil; }
    public void setTerms(List<String> terms) { this.terms = terms; }
    public void setRequiresLoyaltyCard(Boolean requiresLoyaltyCard) { this.requiresLoyaltyCard = requiresLoyaltyCard; }
    public void setCouponCode(String couponCode) { this.couponCode = couponCode; }
    public void setMinimumPurchase(Double minimumPurchase) { this.minimumPurchase = minimumPurchase; }
    public void setEligibleProducts(List<String> eligibleProducts) { this.eligibleProducts = eligibleProducts; }
    public void setExclusions(List<String> exclusions) { this.exclusions = exclusions; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
