package com.java.Zhimdhen_POS.Category.controller;

import com.java.Zhimdhen_POS.Category.model.CategoryDTO;
import com.java.Zhimdhen_POS.Category.service.CategoryService;
import com.java.Zhimdhen_POS.Category.service.CategoryServiceImpl;
import com.java.Zhimdhen_POS.auth.helper.UserInfoDetails;
import com.java.Zhimdhen_POS.product.model.ProductDTO;
import com.java.Zhimdhen_POS.users.model.UserDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    private CategoryServiceImpl categoryServiceImpl;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/by-restaurant")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<CategoryDTO>> getCategoriesByRestaurant(
            @AuthenticationPrincipal UserInfoDetails principal) {

        List<CategoryDTO> categories =
                categoryService.findCategoriesByAdminRestaurant(principal.getRestaurantId());

        return ResponseEntity.ok(categories);
    }

    @GetMapping("/public")
    public List<CategoryDTO> listForCustomer(@RequestParam Long restaurantId) {
        return categoryService.getCategoriesByRestaurantId(restaurantId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        CategoryDTO categoryDTO = categoryService.getCategoryById(id);
        if (categoryDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoryDTO);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO created = categoryServiceImpl.createCategory(categoryDTO);
        return ResponseEntity.status(201).body(created);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id,
                                                      @Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO updatedCategory = categoryService.updateCategory(id, categoryDTO);
        if (updatedCategory == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedCategory);
    }
}
