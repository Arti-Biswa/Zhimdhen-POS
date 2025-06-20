package com.java.Zhimdhen_POS.Category.repository;

import com.java.Zhimdhen_POS.Category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Return List<Category> instead of Optional<Category>
    List<Category> findByName(String name);
}
