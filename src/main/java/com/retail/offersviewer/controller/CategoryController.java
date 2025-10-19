package com.retail.offersviewer.controller;

import com.retail.offersviewer.entity.Category;
import com.retail.offersviewer.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Category operations
 * Provides endpoints for retrieving category information
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    
    private static final Logger log = LoggerFactory.getLogger(CategoryController.class);
    private final CategoryService categoryService;
    
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    
    /**
     * Get all categories
     * @return List of all categories
     */
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        log.info("GET /api/categories - Retrieving all categories");
        List<Category> categories = categoryService.getAllCategories();
        log.info("Returning {} categories", categories.size());
        return ResponseEntity.ok(categories);
    }
}
