package com.retail.offersviewer.service;

import com.retail.offersviewer.entity.Store;
import com.retail.offersviewer.repository.StoreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service layer for Store operations
 * Handles business logic for store retrieval and validation
 */
@Service
@Transactional(readOnly = true)
public class StoreService {
    
    private static final Logger log = LoggerFactory.getLogger(StoreService.class);
    private final StoreRepository storeRepository;
    
    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }
    
    /**
     * Retrieve all stores
     * @return List of all stores
     */
    public List<Store> getAllStores() {
        log.debug("Retrieving all stores");
        List<Store> stores = storeRepository.findAll();
        log.debug("Found {} stores", stores.size());
        return stores;
    }
    
    /**
     * Find store by ID
     * @param storeId The store ID to search for
     * @return Optional containing the store if found
     */
    public Optional<Store> getStoreById(String storeId) {
        log.debug("Retrieving store with ID: {}", storeId);
        
        if (storeId == null || storeId.trim().isEmpty()) {
            log.warn("Store ID is null or empty");
            return Optional.empty();
        }
        
        Optional<Store> store = storeRepository.findById(storeId);
        
        if (store.isPresent()) {
            log.debug("Found store: {}", store.get().getName());
        } else {
            log.debug("Store not found with ID: {}", storeId);
        }
        
        return store;
    }
    
    /**
     * Check if a store exists by ID
     * @param storeId The store ID to check
     * @return true if store exists, false otherwise
     */
    public boolean storeExists(String storeId) {
        if (storeId == null || storeId.trim().isEmpty()) {
            return false;
        }
        return storeRepository.existsById(storeId);
    }
}
