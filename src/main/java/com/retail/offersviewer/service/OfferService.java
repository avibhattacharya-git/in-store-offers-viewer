package com.retail.offersviewer.service;

import com.retail.offersviewer.entity.Offer;
import com.retail.offersviewer.repository.OfferRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service layer for Offer operations Handles business logic for offer
 * retrieval, filtering, searching, and sorting
 */
@Service
@Transactional(readOnly = true)
public class OfferService {

    private static final Logger log = LoggerFactory.getLogger(OfferService.class);
    private final OfferRepository offerRepository;

    public OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    /**
     * Get active offers for a store with optional filtering, searching, and
     * sorting
     *
     * @param storeId The store ID
     * @param category Optional category filter
     * @param searchTerm Optional search term for title/description
     * @param sortBy Optional sort criteria (discount, expiration, category,
     * newest)
     * @return List of filtered and sorted offers
     */
    public List<Offer> getActiveOffers(String storeId, String category, String searchTerm, String sortBy) {
        log.debug("Getting active offers for store: {}, category: {}, search: {}, sortBy: {}",
                storeId, category, searchTerm, sortBy);

        LocalDateTime now = LocalDateTime.now();
        List<Offer> offers;
        
        // Trim inputs to avoid whitespace issues
        String trimmedCategory = category != null ? category.trim() : null;
        String trimmedSearchTerm = searchTerm != null ? searchTerm.trim() : null;

        // Apply filters based on parameters
        if (trimmedSearchTerm != null && !trimmedSearchTerm.isEmpty() && trimmedCategory != null && !trimmedCategory.isEmpty()) {
            // Both search and category filter
            offers = offerRepository.searchActiveOffersByCategory(storeId, trimmedCategory, trimmedSearchTerm, now);
        } else if (trimmedSearchTerm != null && !trimmedSearchTerm.isEmpty()) {
            // Only search filter
            offers = offerRepository.searchActiveOffers(storeId, trimmedSearchTerm, now);
        } else if (trimmedCategory != null && !trimmedCategory.isEmpty()) {
            // Only category filter
            offers = offerRepository.findActiveOffersByStoreIdAndCategory(storeId, trimmedCategory, now);
        } else {
            // No filters, get all active offers
            offers = offerRepository.findActiveOffersByStoreId(storeId, now);
        }

        // Apply sorting
        offers = sortOffers(offers, sortBy);

        log.debug("Found {} active offers", offers.size());
        return offers;
    }

    /**
     * Get offer details by ID
     *
     * @param offerId The offer ID
     * @return Optional containing the offer if found
     */
    public Optional<Offer> getOfferById(String offerId) {
        log.debug("Retrieving offer with ID: {}", offerId);

        if (offerId == null || offerId.trim().isEmpty()) {
            log.warn("Offer ID is null or empty");
            return Optional.empty();
        }

        Optional<Offer> offer = offerRepository.findById(offerId);

        if (offer.isPresent()) {
            log.debug("Found offer: {}", offer.get().getTitle());
        } else {
            log.debug("Offer not found with ID: {}", offerId);
        }

        return offer;
    }

    /**
     * Sort offers based on the specified criteria
     *
     * @param offers List of offers to sort
     * @param sortBy Sort criteria (discount, expiration, category, newest)
     * @return Sorted list of offers
     */
    private List<Offer> sortOffers(List<Offer> offers, String sortBy) {
        if (sortBy == null || sortBy.trim().isEmpty()) {
            return offers;
        }

        log.debug("Sorting offers by: {}", sortBy);

        switch (sortBy.toLowerCase()) {
            case "discount":
                return offers.stream()
                        .sorted(Comparator.comparing(Offer::getDiscountValue).reversed())
                        .collect(Collectors.toList());

            case "expiration":
                return offers.stream()
                        .sorted(Comparator.comparing(Offer::getValidUntil))
                        .collect(Collectors.toList());

            case "category":
                return offers.stream()
                        .sorted(Comparator.comparing(Offer::getCategory))
                        .collect(Collectors.toList());

            case "newest":
                return offers.stream()
                        .sorted(Comparator.comparing(Offer::getCreatedAt).reversed())
                        .collect(Collectors.toList());

            default:
                log.warn("Unknown sort criteria: {}, returning unsorted list", sortBy);
                return offers;
        }
    }

    /**
     * Check if an offer is currently active
     *
     * @param offer The offer to check
     * @return true if offer is active, false otherwise
     */
    public boolean isOfferActive(Offer offer) {
        if (offer == null) {
            return false;
        }
        LocalDateTime now = LocalDateTime.now();
        return !now.isBefore(offer.getValidFrom()) && !now.isAfter(offer.getValidUntil());
    }
}
