package com.retail.offersviewer.service;

import com.retail.offersviewer.entity.Category;
import com.retail.offersviewer.entity.Offer;
import com.retail.offersviewer.repository.CategoryRepository;
import com.retail.offersviewer.repository.OfferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service layer for Category operations
 * Handles business logic for category retrieval and offer counting
 */
@Service
@Transactional(readOnly = true)
public class CategoryService {
    
    private static final Logger log = LoggerFactory.getLogger(CategoryService.class);
    private final CategoryRepository categoryRepository;
    private final OfferRepository offerRepository;
    
    public CategoryService(CategoryRepository categoryRepository, OfferRepository offerRepository) {
        this.categoryRepository = categoryRepository;
        this.offerRepository = offerRepository;
    }
    
    /**
     * Retrieve all categories
     * @return List of all categories
     */
    public List<Category> getAllCategories() {
        log.debug("Retrieving all categories");
        List<Category> categories = categoryRepository.findAll();
        log.debug("Found {} categories", categories.size());
        return categories;
    }
    
    /**
     * Count active offers per category for a specific store
     * @param storeId The store ID to count offers for
     * @return Map of category name to offer count
     */
    public Map<String, Long> countOffersByCategory(String storeId) {
        log.debug("Counting offers by category for store: {}", storeId);
        
        LocalDateTime now = LocalDateTime.now();
        List<Offer> activeOffers = offerRepository.findActiveOffersByStoreId(storeId, now);
        
        Map<String, Long> categoryCount = new HashMap<>();
        
        for (Offer offer : activeOffers) {
            String category = offer.getCategory();
            categoryCount.put(category, categoryCount.getOrDefault(category, 0L) + 1);
        }
        
        log.debug("Category counts: {}", categoryCount);
        return categoryCount;
    }
    
    /**
     * Get all categories with their offer counts for a specific store
     * @param storeId The store ID
     * @return Map of category to offer count (includes categories with 0 offers)
     */
    public Map<Category, Long> getCategoriesWithOfferCount(String storeId) {
        log.debug("Getting categories with offer counts for store: {}", storeId);
        
        List<Category> allCategories = getAllCategories();
        Map<String, Long> offerCounts = countOffersByCategory(storeId);
        
        Map<Category, Long> result = new HashMap<>();
        for (Category category : allCategories) {
            Long count = offerCounts.getOrDefault(category.getName(), 0L);
            result.put(category, count);
        }
        
        return result;
    }
}
