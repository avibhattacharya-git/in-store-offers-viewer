package com.retail.offersviewer.service;

import com.retail.offersviewer.entity.Category;
import com.retail.offersviewer.entity.Offer;
import com.retail.offersviewer.repository.CategoryRepository;
import com.retail.offersviewer.repository.OfferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("CategoryService Tests")
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private OfferRepository offerRepository;

    @InjectMocks
    private CategoryService categoryService;

    private List<Category> testCategories;
    private List<Offer> testOffers;

    @BeforeEach
    void setUp() {
        // Setup test categories
        Category produce = new Category("cat1", "Produce", "🥬");
        Category dairy = new Category("cat2", "Dairy", "🥛");
        Category meat = new Category("cat3", "Meat", "🥩");
        testCategories = Arrays.asList(produce, dairy, meat);

        // Setup test offers
        Offer offer1 = new Offer();
        offer1.setId("offer1");
        offer1.setStoreId("store1");
        offer1.setCategory("Produce");
        offer1.setTitle("Fresh Bananas");
        offer1.setValidFrom(LocalDateTime.now().minusDays(1));
        offer1.setValidUntil(LocalDateTime.now().plusDays(7));

        Offer offer2 = new Offer();
        offer2.setId("offer2");
        offer2.setStoreId("store1");
        offer2.setCategory("Produce");
        offer2.setTitle("Organic Apples");
        offer2.setValidFrom(LocalDateTime.now().minusDays(1));
        offer2.setValidUntil(LocalDateTime.now().plusDays(7));

        Offer offer3 = new Offer();
        offer3.setId("offer3");
        offer3.setStoreId("store1");
        offer3.setCategory("Dairy");
        offer3.setTitle("Milk Gallon");
        offer3.setValidFrom(LocalDateTime.now().minusDays(1));
        offer3.setValidUntil(LocalDateTime.now().plusDays(7));

        testOffers = Arrays.asList(offer1, offer2, offer3);
    }

    @Test
    @DisplayName("Should retrieve all categories")
    void shouldRetrieveAllCategories() {
        // Given
        when(categoryRepository.findAll()).thenReturn(testCategories);

        // When
        List<Category> result = categoryService.getAllCategories();

        // Then
        assertThat(result).hasSize(3);
        assertThat(result).containsExactlyElementsOf(testCategories);
        verify(categoryRepository).findAll();
    }

    @Test
    @DisplayName("Should return empty list when no categories exist")
    void shouldReturnEmptyListWhenNoCategoriesExist() {
        // Given
        when(categoryRepository.findAll()).thenReturn(List.of());

        // When
        List<Category> result = categoryService.getAllCategories();

        // Then
        assertThat(result).isEmpty();
        verify(categoryRepository).findAll();
    }

    @Test
    @DisplayName("Should count offers by category for a store")
    void shouldCountOffersByCategory() {
        // Given
        String storeId = "store1";
        when(offerRepository.findActiveOffersByStoreId(eq(storeId), any(LocalDateTime.class)))
                .thenReturn(testOffers);

        // When
        Map<String, Long> result = categoryService.countOffersByCategory(storeId);

        // Then
        assertThat(result).hasSize(2);
        assertThat(result.get("Produce")).isEqualTo(2L);
        assertThat(result.get("Dairy")).isEqualTo(1L);
        verify(offerRepository).findActiveOffersByStoreId(eq(storeId), any(LocalDateTime.class));
    }

    @Test
    @DisplayName("Should return empty map when no offers exist for store")
    void shouldReturnEmptyMapWhenNoOffersExist() {
        // Given
        String storeId = "store1";
        when(offerRepository.findActiveOffersByStoreId(eq(storeId), any(LocalDateTime.class)))
                .thenReturn(List.of());

        // When
        Map<String, Long> result = categoryService.countOffersByCategory(storeId);

        // Then
        assertThat(result).isEmpty();
        verify(offerRepository).findActiveOffersByStoreId(eq(storeId), any(LocalDateTime.class));
    }

    @Test
    @DisplayName("Should get categories with offer counts including zero counts")
    void shouldGetCategoriesWithOfferCounts() {
        // Given
        String storeId = "store1";
        when(categoryRepository.findAll()).thenReturn(testCategories);
        when(offerRepository.findActiveOffersByStoreId(eq(storeId), any(LocalDateTime.class)))
                .thenReturn(testOffers);

        // When
        Map<Category, Long> result = categoryService.getCategoriesWithOfferCount(storeId);

        // Then
        assertThat(result).hasSize(3);
        assertThat(result.values()).containsExactlyInAnyOrder(2L, 1L, 0L);
        
        // Verify Meat category has 0 offers
        Category meatCategory = testCategories.stream()
                .filter(c -> c.getName().equals("Meat"))
                .findFirst()
                .orElseThrow();
        assertThat(result.get(meatCategory)).isEqualTo(0L);
    }
}
