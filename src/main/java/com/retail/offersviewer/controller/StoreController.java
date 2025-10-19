package com.retail.offersviewer.controller;

import com.retail.offersviewer.entity.Store;
import com.retail.offersviewer.exception.ResourceNotFoundException;
import com.retail.offersviewer.service.StoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Store operations
 * Provides endpoints for retrieving store information
 */
@RestController
@RequestMapping("/api/stores")
public class StoreController {
    
    private static final Logger log = LoggerFactory.getLogger(StoreController.class);
    private final StoreService storeService;
    
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }
    
    /**
     * Get all stores
     * @return List of all stores
     */
    @GetMapping
    public ResponseEntity<List<Store>> getAllStores() {
        log.info("GET /api/stores - Retrieving all stores");
        List<Store> stores = storeService.getAllStores();
        log.info("Returning {} stores", stores.size());
        return ResponseEntity.ok(stores);
    }
    
    /**
     * Get specific store by ID
     * @param storeId The store ID
     * @return Store details or 404 if not found
     */
    @GetMapping("/{storeId}")
    public ResponseEntity<Store> getStoreById(@PathVariable String storeId) {
        log.info("GET /api/stores/{} - Retrieving store", storeId);
        
        Store store = storeService.getStoreById(storeId)
                .orElseThrow(() -> new ResourceNotFoundException("Store", "id", storeId));
        
        log.info("Store found: {}", store.getName());
        return ResponseEntity.ok(store);
    }
}
