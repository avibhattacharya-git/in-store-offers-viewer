package com.retail.offersviewer.config;

import com.retail.offersviewer.entity.Category;
import com.retail.offersviewer.entity.Offer;
import com.retail.offersviewer.entity.Store;
import com.retail.offersviewer.repository.CategoryRepository;
import com.retail.offersviewer.repository.OfferRepository;
import com.retail.offersviewer.repository.StoreRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(StoreRepository storeRepository, 
                                   CategoryRepository categoryRepository,
                                   OfferRepository offerRepository) {
        return args -> {
            // Only initialize if database is empty
            if (storeRepository.count() > 0) {
                return;
            }

            // Create Stores
            Store store1 = new Store();
            store1.setName("King Soopers - Downtown");
            store1.setAddress(new Store.Address(
                "1155 E 9th Ave",
                "Denver",
                "CO",
                "80218"
            ));
            store1.setCoordinates(new Store.Coordinates(39.7294, -104.9738));
            store1 = storeRepository.save(store1);

            Store store2 = new Store();
            store2.setName("King Soopers - Highlands");
            store2.setAddress(new Store.Address(
                "2660 Federal Blvd",
                "Denver",
                "CO",
                "80211"
            ));
            store2.setCoordinates(new Store.Coordinates(39.7547, -105.0253));
            store2 = storeRepository.save(store2);

            Store store3 = new Store();
            store3.setName("Walmart Supercenter");
            store3.setAddress(new Store.Address(
                "3301 Tower Rd",
                "Aurora",
                "CO",
                "80011"
            ));
            store3.setCoordinates(new Store.Coordinates(39.7686, -104.7947));
            store3 = storeRepository.save(store3);

            // Create Categories
            List<String> categoryNames = Arrays.asList(
                "Produce", "Dairy", "Meat", "Bakery", "Household", 
                "Beverages", "Frozen", "Snacks", "Personal Care"
            );
            
            for (String categoryName : categoryNames) {
                Category category = new Category();
                category.setName(categoryName);
                category.setIcon("🏷️");
                categoryRepository.save(category);
            }

            // Create Offers for Store 1 (King Soopers - Downtown)
            createOffer(offerRepository, store1.getId(), "Fresh Organic Bananas", 
                "Sweet and ripe organic bananas", "Produce", 
                Offer.DiscountType.PERCENTAGE, 25.0, 2.99, 2.24,
                LocalDateTime.now(), LocalDateTime.now().plusDays(7),
                Arrays.asList("Limit 5 per customer", "While supplies last"),
                false, null, null,
                Arrays.asList("Organic Bananas"),
                Arrays.asList());

            createOffer(offerRepository, store1.getId(), "Whole Milk Gallon", 
                "Fresh whole milk from local farms", "Dairy", 
                Offer.DiscountType.FIXED, 1.00, 4.99, 3.99,
                LocalDateTime.now(), LocalDateTime.now().plusDays(5),
                Arrays.asList("Limit 2 per customer"),
                true, null, null,
                Arrays.asList("Whole Milk 1 Gallon"),
                Arrays.asList());

            createOffer(offerRepository, store1.getId(), "Buy One Get One Free - Ground Beef", 
                "Premium ground beef 80/20", "Meat", 
                Offer.DiscountType.BOGO, 100.0, 6.99, 3.50,
                LocalDateTime.now(), LocalDateTime.now().plusDays(3),
                Arrays.asList("Buy one get one free", "Equal or lesser value", "Limit 2 offers per customer"),
                false, null, null,
                Arrays.asList("Ground Beef 1lb"),
                Arrays.asList());

            createOffer(offerRepository, store1.getId(), "Artisan Bread Loaf", 
                "Freshly baked sourdough bread", "Bakery", 
                Offer.DiscountType.PERCENTAGE, 30.0, 5.99, 4.19,
                LocalDateTime.now(), LocalDateTime.now().plusDays(2),
                Arrays.asList("Baked fresh daily", "Limit 3 per customer"),
                false, null, null,
                Arrays.asList("Sourdough Bread", "French Bread"),
                Arrays.asList());

            createOffer(offerRepository, store1.getId(), "Laundry Detergent Bundle", 
                "Save on large size detergent", "Household", 
                Offer.DiscountType.BUNDLE, 5.00, 19.99, 14.99,
                LocalDateTime.now(), LocalDateTime.now().plusDays(14),
                Arrays.asList("Must purchase 2 or more", "Mix and match available"),
                false, "CLEAN2024", 20.00,
                Arrays.asList("Tide 100oz", "Gain 100oz", "All 100oz"),
                Arrays.asList("Travel sizes"));

            // Create Offers for Store 2 (King Soopers - Highlands)
            createOffer(offerRepository, store2.getId(), "Fresh Strawberries", 
                "Sweet California strawberries", "Produce", 
                Offer.DiscountType.PERCENTAGE, 40.0, 4.99, 2.99,
                LocalDateTime.now(), LocalDateTime.now().plusDays(4),
                Arrays.asList("Limit 4 per customer", "While supplies last"),
                false, null, null,
                Arrays.asList("Strawberries 1lb"),
                Arrays.asList());

            createOffer(offerRepository, store2.getId(), "Greek Yogurt 4-Pack", 
                "Creamy Greek yogurt variety pack", "Dairy", 
                Offer.DiscountType.FIXED, 2.00, 5.99, 3.99,
                LocalDateTime.now(), LocalDateTime.now().plusDays(10),
                Arrays.asList("All flavors included"),
                true, null, null,
                Arrays.asList("Chobani 4-pack", "Fage 4-pack"),
                Arrays.asList());

            createOffer(offerRepository, store2.getId(), "Chicken Breast Family Pack", 
                "Boneless skinless chicken breast", "Meat", 
                Offer.DiscountType.PERCENTAGE, 35.0, 12.99, 8.44,
                LocalDateTime.now(), LocalDateTime.now().plusDays(6),
                Arrays.asList("Family pack 3lbs or more", "Fresh never frozen"),
                false, null, null,
                Arrays.asList("Chicken Breast Family Pack"),
                Arrays.asList());

            createOffer(offerRepository, store2.getId(), "Soft Drinks 12-Pack", 
                "Popular soda brands on sale", "Beverages", 
                Offer.DiscountType.BUNDLE, 3.00, 6.99, 3.99,
                LocalDateTime.now(), LocalDateTime.now().plusDays(7),
                Arrays.asList("Must buy 3 or more", "Mix and match"),
                false, null, null,
                Arrays.asList("Coca-Cola 12pk", "Pepsi 12pk", "Sprite 12pk", "Dr Pepper 12pk"),
                Arrays.asList());

            createOffer(offerRepository, store2.getId(), "Ice Cream Pints", 
                "Premium ice cream varieties", "Frozen", 
                Offer.DiscountType.BOGO, 100.0, 5.99, 3.00,
                LocalDateTime.now(), LocalDateTime.now().plusDays(12),
                Arrays.asList("Buy one get one free", "Equal or lesser value"),
                true, null, null,
                Arrays.asList("Ben & Jerry's", "Haagen-Dazs"),
                Arrays.asList());

            // Create Offers for Store 3 (Walmart)
            createOffer(offerRepository, store3.getId(), "Mixed Salad Greens", 
                "Fresh spring mix salad", "Produce", 
                Offer.DiscountType.PERCENTAGE, 20.0, 3.99, 3.19,
                LocalDateTime.now(), LocalDateTime.now().plusDays(5),
                Arrays.asList("Organic option available"),
                false, null, null,
                Arrays.asList("Spring Mix 5oz", "Baby Spinach 5oz"),
                Arrays.asList());

            createOffer(offerRepository, store3.getId(), "Cheese Variety Pack", 
                "Assorted cheese slices", "Dairy", 
                Offer.DiscountType.FIXED, 1.50, 4.99, 3.49,
                LocalDateTime.now(), LocalDateTime.now().plusDays(8),
                Arrays.asList("Includes cheddar, swiss, and provolone"),
                false, null, null,
                Arrays.asList("Kraft Cheese Variety"),
                Arrays.asList());

            createOffer(offerRepository, store3.getId(), "Pork Chops Value Pack", 
                "Bone-in pork chops", "Meat", 
                Offer.DiscountType.PERCENTAGE, 30.0, 9.99, 6.99,
                LocalDateTime.now(), LocalDateTime.now().plusDays(4),
                Arrays.asList("Value pack 2lbs or more"),
                false, null, null,
                Arrays.asList("Pork Chops Bone-in"),
                Arrays.asList());

            createOffer(offerRepository, store3.getId(), "Potato Chips Family Size", 
                "Crispy potato chips", "Snacks", 
                Offer.DiscountType.BUNDLE, 2.00, 4.99, 2.99,
                LocalDateTime.now(), LocalDateTime.now().plusDays(15),
                Arrays.asList("Buy 2 get $2 off each", "Mix and match flavors"),
                false, null, null,
                Arrays.asList("Lay's Family Size", "Ruffles Family Size", "Doritos Family Size"),
                Arrays.asList());

            createOffer(offerRepository, store3.getId(), "Shampoo & Conditioner Set", 
                "Hair care bundle deal", "Personal Care", 
                Offer.DiscountType.PERCENTAGE, 25.0, 15.99, 11.99,
                LocalDateTime.now(), LocalDateTime.now().plusDays(20),
                Arrays.asList("Includes both shampoo and conditioner"),
                false, "HAIR25", null,
                Arrays.asList("Pantene Set", "Herbal Essences Set"),
                Arrays.asList("Travel sizes"));

            createOffer(offerRepository, store3.getId(), "Frozen Pizza", 
                "Deluxe frozen pizza varieties", "Frozen", 
                Offer.DiscountType.FIXED, 3.00, 8.99, 5.99,
                LocalDateTime.now(), LocalDateTime.now().plusDays(10),
                Arrays.asList("All varieties included"),
                false, null, null,
                Arrays.asList("DiGiorno Pizza", "Red Baron Pizza"),
                Arrays.asList());

            System.out.println("Database initialized with sample data:");
            System.out.println("- 3 stores created");
            System.out.println("- 9 categories created");
            System.out.println("- " + offerRepository.count() + " offers created");
        };
    }

    private void createOffer(OfferRepository repository, String storeId, String title, 
                           String description, String category, Offer.DiscountType discountType,
                           Double discountValue, Double originalPrice, Double finalPrice,
                           LocalDateTime validFrom, LocalDateTime validUntil,
                           List<String> terms, Boolean requiresLoyaltyCard, String couponCode,
                           Double minimumPurchase, List<String> eligibleProducts, 
                           List<String> exclusions) {
        Offer offer = new Offer();
        offer.setStoreId(storeId);
        offer.setTitle(title);
        offer.setDescription(description);
        offer.setCategory(category);
        offer.setDiscountType(discountType);
        offer.setDiscountValue(discountValue);
        offer.setOriginalPrice(originalPrice);
        offer.setFinalPrice(finalPrice);
        offer.setValidFrom(validFrom);
        offer.setValidUntil(validUntil);
        offer.setTerms(terms);
        offer.setRequiresLoyaltyCard(requiresLoyaltyCard);
        offer.setCouponCode(couponCode);
        offer.setMinimumPurchase(minimumPurchase);
        offer.setEligibleProducts(eligibleProducts);
        offer.setExclusions(exclusions);
        offer.setCreatedAt(LocalDateTime.now());
        repository.save(offer);
    }
}
