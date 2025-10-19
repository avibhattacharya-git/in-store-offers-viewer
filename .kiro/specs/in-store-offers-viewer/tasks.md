# Implementation Plan

- [x] 1. Set up Spring Boot backend project structure





  - Create Maven project with Spring Boot 3.x and Java 17+
  - Add dependencies: Spring Web, Spring Data JPA, HSQLDB, PostgreSQL driver, Lombok, Validation
  - Configure application.properties for dev and prod profiles
  - Set up package structure: controller, service, repository, entity, exception, config
  - _Requirements: 1.1, 4.1_

- [x] 2. Implement database entities and repositories




- [x] 2.1 Create JPA entity classes


  - Write Store entity with embedded Address and Coordinates
  - Write Offer entity with all fields, indexes, and element collections
  - Write Category entity
  - Add proper JPA annotations, constraints, and relationships
  - _Requirements: 1.2, 1.3, 5.1_

- [x] 2.2 Create repository interfaces


  - Write StoreRepository extending JpaRepository
  - Write OfferRepository with custom query methods for filtering and searching
  - Write CategoryRepository extending JpaRepository
  - Add method signatures for finding offers by store, category, and search terms
  - _Requirements: 1.1, 2.1, 2.2, 4.2_

- [x] 3. Implement service layer with business logic





- [x] 3.1 Create StoreService


  - Write methods to retrieve all stores and find store by ID
  - Implement validation logic
  - _Requirements: 4.1, 4.2_

- [x] 3.2 Create OfferService


  - Write method to get active offers for a store (filter expired offers)
  - Implement search functionality (title and description matching)
  - Implement category filtering
  - Implement sorting logic (by discount, expiration, category, newest)
  - Write method to get offer details by ID
  - _Requirements: 1.1, 1.4, 2.1, 2.2, 2.3, 3.1, 6.1, 6.2, 6.3, 6.5_

- [x] 3.3 Create CategoryService


  - Write method to retrieve all categories
  - Implement logic to count offers per category
  - _Requirements: 5.1, 5.3_

- [x] 4. Implement REST controllers




- [x] 4.1 Create StoreController


  - Write GET /api/stores endpoint to return all stores
  - Write GET /api/stores/{storeId} endpoint to return specific store
  - Add proper response status codes and error handling
  - _Requirements: 4.1, 4.2_

- [x] 4.2 Create OfferController


  - Write GET /api/stores/{storeId}/offers endpoint with query parameters
  - Write GET /api/offers/{offerId} endpoint for offer details
  - Add request validation for query parameters
  - Add proper response status codes
  - _Requirements: 1.1, 1.2, 1.3, 2.1, 2.2, 3.1, 3.2, 6.1_

- [x] 4.3 Create CategoryController


  - Write GET /api/categories endpoint to return all categories
  - _Requirements: 5.1, 5.2_

- [x] 5. Implement global exception handling






  - Create custom ResourceNotFoundException
  - Create ErrorResponse DTO class
  - Write GlobalExceptionHandler with @ControllerAdvice
  - Handle ResourceNotFoundException (404)
  - Handle validation exceptions (400)
  - Handle generic exceptions (500)
  - _Requirements: 1.1, 3.1, 4.2_

- [x] 6. Configure CORS and application settings





  - Create WebConfig class implementing WebMvcConfigurer
  - Configure CORS to allow frontend origin
  - Set up application-dev.properties for HSQLDB
  - Set up application-prod.properties for PostgreSQL
  - _Requirements: 1.1_

- [x] 7. Create database initialization script






  - Write data.sql or use CommandLineRunner to populate initial data
  - Add sample stores (2-3 stores with addresses)
  - Add sample categories (Produce, Dairy, Meat, Bakery, Household, etc.)
  - Add sample offers for each store with various discount types
  - Ensure offers have valid date ranges
  - _Requirements: 1.1, 4.1, 5.1_

- [ ] 8. Set up React frontend project
  - Create Vite project with React and TypeScript
  - Install dependencies: React Router, TanStack Query, Tailwind CSS, Axios
  - Configure Tailwind CSS
  - Set up project structure: components, hooks, services, types, pages
  - Create API base URL configuration
  - _Requirements: 1.1_

- [ ] 9. Create TypeScript type definitions
  - Write Store, Offer, and Category interfaces matching backend models
  - Create API response types
  - Create filter and sort option types
  - _Requirements: 1.2, 3.2_

- [ ] 10. Implement API service layer
  - Create axios instance with base configuration
  - Write API functions for fetching stores
  - Write API functions for fetching offers with query parameters
  - Write API function for fetching offer details
  - Write API function for fetching categories
  - Add error handling and response transformation
  - _Requirements: 1.1, 1.5, 2.1, 3.1, 4.2_

- [ ] 11. Create custom React hooks for data fetching
  - Write useStores hook using TanStack Query
  - Write useOffers hook with parameters for storeId, filters, search, sort
  - Write useOfferDetails hook for single offer
  - Write useCategories hook
  - Configure appropriate cache times
  - _Requirements: 1.1, 1.5, 2.1, 3.1_

- [ ] 12. Implement store selection functionality
  - Create StoreContext for managing selected store
  - Write StoreSelector component with dropdown of stores
  - Implement localStorage persistence for selected store
  - Add store selection prompt on first visit
  - Display currently selected store in UI
  - _Requirements: 4.1, 4.2, 4.3, 4.4_

- [ ] 13. Build main offers list view
- [ ] 13.1 Create OfferCard component
  - Display offer title, discount value, and discount type
  - Show expiration date with formatting
  - Display product image or placeholder
  - Add click handler to navigate to details
  - Style with Tailwind CSS for responsive design
  - _Requirements: 1.2, 1.3_

- [ ] 13.2 Create OffersListView component
  - Fetch offers using useOffers hook
  - Display loading indicator while fetching
  - Render grid of OfferCard components
  - Handle empty state when no offers available
  - Implement responsive grid layout
  - _Requirements: 1.1, 1.5, 2.4_

- [ ] 14. Implement search functionality
  - Create SearchBar component with text input
  - Implement debounced search (300ms delay)
  - Add clear button to reset search
  - Update offers query when search term changes
  - Display search term in UI
  - _Requirements: 2.1, 2.5_

- [ ] 15. Implement filter functionality
  - Create FilterPanel component
  - Display category checkboxes using useCategories hook
  - Implement multi-select category filtering
  - Add clear all filters button
  - Update offers query when filters change
  - Show active filter count
  - _Requirements: 2.2, 2.3, 2.5, 5.2_

- [ ] 16. Implement sort functionality
  - Create SortControl component with dropdown
  - Add sort options: discount amount, expiration date, category, newest
  - Update offers query when sort option changes
  - Display current sort option
  - _Requirements: 6.1, 6.2, 6.3, 6.5_

- [ ] 17. Build offer details view
  - Create OfferDetailsView component
  - Fetch offer details using useOfferDetails hook
  - Display complete offer information (title, description, discount, dates)
  - Show terms and conditions list
  - Display eligible products and exclusions
  - Highlight loyalty card or coupon code requirements
  - Add back button to return to offers list
  - Handle loading and error states
  - _Requirements: 3.1, 3.2, 3.3, 3.4, 3.5_

- [ ] 18. Implement routing and navigation
  - Set up React Router with routes
  - Create route for offers list view (/)
  - Create route for offer details (/offers/:offerId)
  - Implement navigation between views
  - Handle invalid routes with 404 page
  - _Requirements: 3.1, 3.4_

- [ ] 19. Add error handling and user feedback
  - Create ErrorMessage component for displaying errors
  - Implement error boundaries for React components
  - Add retry functionality for failed requests
  - Display user-friendly error messages
  - Show "no offers found" message when filters return empty results
  - _Requirements: 1.5, 2.4_

- [ ] 20. Implement category view (optional alternative layout)
  - Create CategoryView component
  - Group offers by category
  - Create collapsible category sections
  - Display offer count in category headers
  - Add toggle to switch between list and category view
  - _Requirements: 5.1, 5.2, 5.3, 5.4, 5.5_

- [ ] 21. Add responsive design and accessibility
  - Ensure all components are mobile-responsive
  - Implement proper keyboard navigation
  - Add ARIA labels for screen readers
  - Ensure sufficient color contrast
  - Test with keyboard-only navigation
  - Add focus indicators for interactive elements
  - _Requirements: 1.1, 1.2_

- [ ] 22. Create App component and wire everything together
  - Set up TanStack Query provider
  - Set up React Router
  - Integrate StoreContext provider
  - Create main layout with header showing selected store
  - Wire up all routes and components
  - Add global styles and theme
  - _Requirements: 1.1, 4.4_
