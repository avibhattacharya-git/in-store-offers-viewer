package com.retail.offersviewer.service;

import com.retail.offersviewer.entity.Offer;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("OfferService Tests")
class OfferServiceTest {

    @Mock
    private OfferRepository offerRepository;

    @InjectMocks
    private OfferService offerService;

    private List<Offer> testOffers;
    private Offer offer1;
    private Offer offer2;
    private Offer offer3;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();

        offer1 = new Offer();
        offer1.setId("offer1");
        offer1.setStoreId("store1");
        offer1.setTitle("Fresh Bananas");
        offer1.setDescription("Sweet and ripe");
        offer1.setCategory("Produce");
        offer1.setDiscountType(Offer.DiscountType.PERCENTAGE);
        offer1.setDiscountValue(25.0);
        offer1.setValidFrom(now.minusDays(1));
        offer1.setValidUntil(now.plusDays(7));
        offer1.setCreatedAt(now.minusDays(5));

        offer2 = new Offer();
        offer2.setId("offer2");
        offer2.setStoreId("store1");
        offer2.setTitle("Milk Gallon");
        offer2.setDescription("Fresh whole milk");
        offer2.setCategory("Dairy");
        offer2.setDiscountType(Offer.DiscountType.FIXED);
        offer2.setDiscountValue(1.0);
        offer2.setValidFrom(now.minusDays(1));
        offer2.setValidUntil(now.plusDays(3));
        offer2.setCreatedAt(now.minusDays(3));

        offer3 = new Offer();
        offer3.setId("offer3");
        offer3.setStoreId("store1");
        offer3.setTitle("Ground Beef");
        offer3.setDescription("Premium quality");
        offer3.setCategory("Meat");
        offer3.setDiscountType(Offer.DiscountType.BOGO);
        offer3.setDiscountValue(50.0);
        offer3.setValidFrom(now.minusDays(1));
        offer3.setValidUntil(now.plusDays(5));
        offer3.setCreatedAt(now.minusDays(1));

        testOffers = Arrays.asList(offer1, offer2, offer3);
    }

    @Test
    @DisplayName("Should get all active offers without filters")
    void shouldGetAllActiveOffersWithoutFilters() {
        // Given
        String storeId = "store1";
        when(offerRepository.findActiveOffersByStoreId(eq(storeId), any(LocalDateTime.class)))
                .thenReturn(testOffers);

        // When
        List<Offer> result = offerService.getActiveOffers(storeId, null, null, null);

        // Then
        assertThat(result).hasSize(3);
        assertThat(result).containsExactlyElementsOf(testOffers);
        verify(offerRepository).findActiveOffersByStoreId(eq(storeId), any(LocalDateTime.class));
    }

    @Test
    @DisplayName("Should filter offers by category")
    void shouldFilterOffersByCategory() {
        // Given
        String storeId = "store1";
        String category = "Produce";
        when(offerRepository.findActiveOffersByStoreIdAndCategory(eq(storeId), eq(category), any(LocalDateTime.class)))
                .thenReturn(List.of(offer1));

        // When
        List<Offer> result = offerService.getActiveOffers(storeId, category, null, null);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCategory()).isEqualTo("Produce");
        verify(offerRepository).findActiveOffersByStoreIdAndCategory(eq(storeId), eq(category), any(LocalDateTime.class));
    }

    @Test
    @DisplayName("Should search offers by term")
    void shouldSearchOffersByTerm() {
        // Given
        String storeId = "store1";
        String searchTerm = "milk";
        when(offerRepository.searchActiveOffers(eq(storeId), eq(searchTerm), any(LocalDateTime.class)))
                .thenReturn(List.of(offer2));

        // When
        List<Offer> result = offerService.getActiveOffers(storeId, null, searchTerm, null);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).containsIgnoringCase("milk");
        verify(offerRepository).searchActiveOffers(eq(storeId), eq(searchTerm), any(LocalDateTime.class));
    }

    @Test
    @DisplayName("Should filter by category and search term")
    void shouldFilterByCategoryAndSearchTerm() {
        // Given
        String storeId = "store1";
        String category = "Produce";
        String searchTerm = "banana";
        when(offerRepository.searchActiveOffersByCategory(eq(storeId), eq(category), eq(searchTerm), any(LocalDateTime.class)))
                .thenReturn(List.of(offer1));

        // When
        List<Offer> result = offerService.getActiveOffers(storeId, category, searchTerm, null);

        // Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCategory()).isEqualTo("Produce");
        assertThat(result.get(0).getTitle()).containsIgnoringCase("banana");
        verify(offerRepository).searchActiveOffersByCategory(eq(storeId), eq(category), eq(searchTerm), any(LocalDateTime.class));
    }

    @Test
    @DisplayName("Should sort offers by discount value descending")
    void shouldSortOffersByDiscountDescending() {
        // Given
        String storeId = "store1";
        when(offerRepository.findActiveOffersByStoreId(eq(storeId), any(LocalDateTime.class)))
                .thenReturn(testOffers);

        // When
        List<Offer> result = offerService.getActiveOffers(storeId, null, null, "discount");

        // Then
        assertThat(result).hasSize(3);
        assertThat(result.get(0).getDiscountValue()).isEqualTo(50.0);
        assertThat(result.get(1).getDiscountValue()).isEqualTo(25.0);
        assertThat(result.get(2).getDiscountValue()).isEqualTo(1.0);
    }

    @Test
    @DisplayName("Should sort offers by expiration date ascending")
    void shouldSortOffersByExpirationAscending() {
        // Given
        String storeId = "store1";
        when(offerRepository.findActiveOffersByStoreId(eq(storeId), any(LocalDateTime.class)))
                .thenReturn(testOffers);

        // When
        List<Offer> result = offerService.getActiveOffers(storeId, null, null, "expiration");

        // Then
        assertThat(result).hasSize(3);
        assertThat(result.get(0).getId()).isEqualTo("offer2"); // Expires in 3 days
        assertThat(result.get(1).getId()).isEqualTo("offer3"); // Expires in 5 days
        assertThat(result.get(2).getId()).isEqualTo("offer1"); // Expires in 7 days
    }

    @Test
    @DisplayName("Should sort offers by category alphabetically")
    void shouldSortOffersByCategoryAlphabetically() {
        // Given
        String storeId = "store1";
        when(offerRepository.findActiveOffersByStoreId(eq(storeId), any(LocalDateTime.class)))
                .thenReturn(testOffers);

        // When
        List<Offer> result = offerService.getActiveOffers(storeId, null, null, "category");

        // Then
        assertThat(result).hasSize(3);
        assertThat(result.get(0).getCategory()).isEqualTo("Dairy");
        assertThat(result.get(1).getCategory()).isEqualTo("Meat");
        assertThat(result.get(2).getCategory()).isEqualTo("Produce");
    }

    @Test
    @DisplayName("Should sort offers by newest first")
    void shouldSortOffersByNewestFirst() {
        // Given
        String storeId = "store1";
        when(offerRepository.findActiveOffersByStoreId(eq(storeId), any(LocalDateTime.class)))
                .thenReturn(testOffers);

        // When
        List<Offer> result = offerService.getActiveOffers(storeId, null, null, "newest");

        // Then
        assertThat(result).hasSize(3);
        assertThat(result.get(0).getId()).isEqualTo("offer3"); // Created 1 day ago
        assertThat(result.get(1).getId()).isEqualTo("offer2"); // Created 3 days ago
        assertThat(result.get(2).getId()).isEqualTo("offer1"); // Created 5 days ago
    }

    @Test
    @DisplayName("Should return unsorted list for unknown sort criteria")
    void shouldReturnUnsortedListForUnknownSortCriteria() {
        // Given
        String storeId = "store1";
        when(offerRepository.findActiveOffersByStoreId(eq(storeId), any(LocalDateTime.class)))
                .thenReturn(testOffers);

        // When
        List<Offer> result = offerService.getActiveOffers(storeId, null, null, "invalid");

        // Then
        assertThat(result).hasSize(3);
        assertThat(result).containsExactlyElementsOf(testOffers);
    }

    @Test
    @DisplayName("Should get offer by ID")
    void shouldGetOfferById() {
        // Given
        String offerId = "offer1";
        when(offerRepository.findById(offerId)).thenReturn(Optional.of(offer1));

        // When
        Optional<Offer> result = offerService.getOfferById(offerId);

        // Then
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(offer1);
        assertThat(result.get().getTitle()).isEqualTo("Fresh Bananas");
        verify(offerRepository).findById(offerId);
    }

    @Test
    @DisplayName("Should return empty when offer not found")
    void shouldReturnEmptyWhenOfferNotFound() {
        // Given
        String offerId = "nonexistent";
        when(offerRepository.findById(offerId)).thenReturn(Optional.empty());

        // When
        Optional<Offer> result = offerService.getOfferById(offerId);

        // Then
        assertThat(result).isEmpty();
        verify(offerRepository).findById(offerId);
    }

    @Test
    @DisplayName("Should return empty when offer ID is null")
    void shouldReturnEmptyWhenOfferIdIsNull() {
        // When
        Optional<Offer> result = offerService.getOfferById(null);

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should return empty when offer ID is empty")
    void shouldReturnEmptyWhenOfferIdIsEmpty() {
        // When
        Optional<Offer> result = offerService.getOfferById("");

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should return true for active offer")
    void shouldReturnTrueForActiveOffer() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Offer activeOffer = new Offer();
        activeOffer.setValidFrom(now.minusDays(1));
        activeOffer.setValidUntil(now.plusDays(1));

        // When
        boolean result = offerService.isOfferActive(activeOffer);

        // Then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Should return false for expired offer")
    void shouldReturnFalseForExpiredOffer() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Offer expiredOffer = new Offer();
        expiredOffer.setValidFrom(now.minusDays(10));
        expiredOffer.setValidUntil(now.minusDays(1));

        // When
        boolean result = offerService.isOfferActive(expiredOffer);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Should return false for future offer")
    void shouldReturnFalseForFutureOffer() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Offer futureOffer = new Offer();
        futureOffer.setValidFrom(now.plusDays(1));
        futureOffer.setValidUntil(now.plusDays(10));

        // When
        boolean result = offerService.isOfferActive(futureOffer);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Should return false for null offer")
    void shouldReturnFalseForNullOffer() {
        // When
        boolean result = offerService.isOfferActive(null);

        // Then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Should trim whitespace from search term")
    void shouldTrimWhitespaceFromSearchTerm() {
        // Given
        String storeId = "store1";
        String searchTerm = "  milk  ";
        when(offerRepository.searchActiveOffers(eq(storeId), eq("milk"), any(LocalDateTime.class)))
                .thenReturn(List.of(offer2));

        // When
        List<Offer> result = offerService.getActiveOffers(storeId, null, searchTerm, null);

        // Then
        assertThat(result).hasSize(1);
        verify(offerRepository).searchActiveOffers(eq(storeId), eq("milk"), any(LocalDateTime.class));
    }

    @Test
    @DisplayName("Should trim whitespace from category")
    void shouldTrimWhitespaceFromCategory() {
        // Given
        String storeId = "store1";
        String category = "  Produce  ";
        when(offerRepository.findActiveOffersByStoreIdAndCategory(eq(storeId), eq("Produce"), any(LocalDateTime.class)))
                .thenReturn(List.of(offer1));

        // When
        List<Offer> result = offerService.getActiveOffers(storeId, category, null, null);

        // Then
        assertThat(result).hasSize(1);
        verify(offerRepository).findActiveOffersByStoreIdAndCategory(eq(storeId), eq("Produce"), any(LocalDateTime.class));
    }
}
