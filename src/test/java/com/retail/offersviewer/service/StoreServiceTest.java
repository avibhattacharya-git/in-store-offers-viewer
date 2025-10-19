package com.retail.offersviewer.service;

import com.retail.offersviewer.entity.Store;
import com.retail.offersviewer.repository.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("StoreService Tests")
class StoreServiceTest {

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private StoreService storeService;

    private Store testStore;
    private List<Store> testStores;

    @BeforeEach
    void setUp() {
        Store.Address address = new Store.Address("123 Main St", "Denver", "CO", "80202");
        Store.Coordinates coordinates = new Store.Coordinates(39.7392, -104.9903);
        
        testStore = new Store("store1", "King Soopers Downtown", address, coordinates);
        
        Store store2 = new Store("store2", "Walmart Supercenter", 
                new Store.Address("456 Oak Ave", "Aurora", "CO", "80011"),
                new Store.Coordinates(39.7294, -104.8319));
        
        testStores = Arrays.asList(testStore, store2);
    }

    @Test
    @DisplayName("Should retrieve all stores")
    void shouldRetrieveAllStores() {
        // Given
        when(storeRepository.findAll()).thenReturn(testStores);

        // When
        List<Store> result = storeService.getAllStores();

        // Then
        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyElementsOf(testStores);
        verify(storeRepository).findAll();
    }

    @Test
    @DisplayName("Should return empty list when no stores exist")
    void shouldReturnEmptyListWhenNoStoresExist() {
        // Given
        when(storeRepository.findAll()).thenReturn(List.of());

        // When
        List<Store> result = storeService.getAllStores();

        // Then
        assertThat(result).isEmpty();
        verify(storeRepository).findAll();
    }

    @Test
    @DisplayName("Should find store by ID")
    void shouldFindStoreById() {
        // Given
        String storeId = "store1";
        when(storeRepository.findById(storeId)).thenReturn(Optional.of(testStore));

        // When
        Optional<Store> result = storeService.getStoreById(storeId);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(testStore);
        assertThat(result.get().getName()).isEqualTo("King Soopers Downtown");
        verify(storeRepository).findById(storeId);
    }

    @Test
    @DisplayName("Should return empty when store not found")
    void shouldReturnEmptyWhenStoreNotFound() {
        // Given
        String storeId = "nonexistent";
        when(storeRepository.findById(storeId)).thenReturn(Optional.empty());

        // When
        Optional<Store> result = storeService.getStoreById(storeId);

        // Then
        assertThat(result).isEmpty();
        verify(storeRepository).findById(storeId);
    }

    @Test
    @DisplayName("Should return empty when store ID is null")
    void shouldReturnEmptyWhenStoreIdIsNull() {
        // When
        Optional<Store> result = storeService.getStoreById(null);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should return empty when store ID is empty")
    void shouldReturnEmptyWhenStoreIdIsEmpty() {
        // When
        Optional<Store> result = storeService.getStoreById("");

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should return empty when store ID is blank")
    void shouldReturnEmptyWhenStoreIdIsBlank() {
        // When
        Optional<Store> result = storeService.getStoreById("   ");

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should return true when store exists")
    void shouldReturnTrueWhenStoreExists() {
        // Given
        String storeId = "store1";
        when(storeRepository.existsById(storeId)).thenReturn(true);

        // When
        boolean result = storeService.storeExists(storeId);

        // Then
        assertThat(result).isTrue();
        verify(storeRepository).existsById(storeId);
    }

    @Test
    @DisplayName("Should return false when store does not exist")
    void shouldReturnFalseWhenStoreDoesNotExist() {
        // Given
        String storeId = "nonexistent";
        when(storeRepository.existsById(storeId)).thenReturn(false);

        // When
        boolean result = storeService.storeExists(storeId);

        // Then
        assertThat(result).isFalse();
        verify(storeRepository).existsById(storeId);
    }

    @Test
    @DisplayName("Should return false when checking existence with null ID")
    void shouldReturnFalseWhenCheckingExistenceWithNullId() {
        // When
        boolean result = storeService.storeExists(null);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Should return false when checking existence with empty ID")
    void shouldReturnFalseWhenCheckingExistenceWithEmptyId() {
        // When
        boolean result = storeService.storeExists("");

        // Then
        assertThat(result).isFalse();
    }
}
