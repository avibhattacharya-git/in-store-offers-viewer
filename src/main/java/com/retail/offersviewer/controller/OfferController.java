package com.retail.offersviewer.controller;

import com.retail.offersviewer.entity.Offer;
import com.retail.offersviewer.service.OfferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Offer operations
 * Provides endpoints for retrieving and filtering offers
 */
@RestController
@RequestMapping("/api")
public class OfferController {
    
    private static final Logger log = LoggerFactory.getLogger(OfferController.class);
    private final OfferService offerService;
    
    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }
    
    /**
     * Get all active offers for a specific store with optional filtering and sorting
     * @param storeId The store ID
     * @param category Optional category filter
     * @param search Optional search term for title/description
     * @param sortBy Optional sort criteria (discount, expiration, category, newest)
     * @return List of filtered and sorted offers
     */
    @GetMapping("/stores/{storeId}/offers")
    public ResponseEntity<List<Offer>> getStoreOffers(
            @PathVariable String storeId,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sortBy) {
        
        log.info("GET /api/stores/{}/offers - category: {}, search: {}, sortBy: {}", 
                 storeId, category, search, sortBy);
        
        List<Offer> offers = offerService.getActiveOffers(storeId, category, search, sortBy);
        log.info("Returning {} offers", offers.size());
        
        return ResponseEntity.ok(offers);
    }
    
    /**
     * Get specific offer details by ID
     * @param offerId The offer ID
     * @return Offer details or 404 if not found
     */
    @GetMapping("/offers/{offerId}")
    public ResponseEntity<Offer> getOfferById(@PathVariable String offerId) {
        log.info("GET /api/offers/{} - Retrieving offer details", offerId);
        
        return offerService.getOfferById(offerId)
                .map(offer -> {
                    log.info("Offer found: {}", offer.getTitle());
                    return ResponseEntity.ok(offer);
                })
                .orElseGet(() -> {
                    log.warn("Offer not found with ID: {}", offerId);
                    return ResponseEntity.notFound().build();
                });
    }
}
