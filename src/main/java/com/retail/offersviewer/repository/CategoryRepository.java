package com.retail.offersviewer.repository;

import com.retail.offersviewer.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    
    /**
     * Find category by name
     */
    Optional<Category> findByName(String name);
}
