package com.retail.offersviewer.repository;

import com.retail.offersviewer.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, String> {
    
    /**
     * Find all active offers for a specific store
     * Active offers are those where current time is between validFrom and validUntil
     */
    @Query("SELECT o FROM Offer o WHERE o.storeId = :storeId AND o.validUntil >= :currentTime")
    List<Offer> findActiveOffersByStoreId(@Param("storeId") String storeId, @Param("currentTime") LocalDateTime currentTime);
    
    /**
     * Find active offers by store and category
     */
    @Query("SELECT o FROM Offer o WHERE o.storeId = :storeId AND o.category = :category AND o.validUntil >= :currentTime")
    List<Offer> findActiveOffersByStoreIdAndCategory(
        @Param("storeId") String storeId, 
        @Param("category") String category, 
        @Param("currentTime") LocalDateTime currentTime
    );
    
    /**
     * Search active offers by title or description
     */
    @Query("SELECT o FROM Offer o WHERE o.storeId = :storeId " +
           "AND (LOWER(o.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR LOWER(o.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) " +
           "AND o.validUntil >= :currentTime")
    List<Offer> searchActiveOffers(
        @Param("storeId") String storeId, 
        @Param("searchTerm") String searchTerm, 
        @Param("currentTime") LocalDateTime currentTime
    );
    
    /**
     * Search active offers by title or description with category filter
     */
    @Query("SELECT o FROM Offer o WHERE o.storeId = :storeId " +
           "AND o.category = :category " +
           "AND (LOWER(o.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "OR LOWER(o.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) " +
           "AND o.validUntil >= :currentTime")
    List<Offer> searchActiveOffersByCategory(
        @Param("storeId") String storeId, 
        @Param("category") String category,
        @Param("searchTerm") String searchTerm, 
        @Param("currentTime") LocalDateTime currentTime
    );
    
    /**
     * Find all offers by store (including expired ones, for admin purposes)
     */
    List<Offer> findByStoreId(String storeId);
    
    /**
     * Find offers by category
     */
    List<Offer> findByCategory(String category);
}
