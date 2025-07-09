package com.java.Zhimdhen_POS.Category.service;

import com.java.Zhimdhen_POS.Category.mapper.CategoryMapper;
import com.java.Zhimdhen_POS.Category.model.Category;
import com.java.Zhimdhen_POS.Category.model.CategoryDTO;
import com.java.Zhimdhen_POS.Category.repository.CategoryRepository;
import com.java.Zhimdhen_POS.restaurant.model.Restaurant;
import com.java.Zhimdhen_POS.restaurant.repository.RestaurantRepository;
import com.java.Zhimdhen_POS.users.model.User;
import com.java.Zhimdhen_POS.users.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private  CategoryMapper categoryMapper;


    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(CategoryMapper::toDTO)
                .orElse(null);
    }

    @Override
    @Transactional
    public CategoryDTO createCategory(CategoryDTO dto) {

        /* 1️⃣  Grab the email that Spring Security put in the Authentication object */
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        /* 2️⃣  Load the admin by that email */
        User admin = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Logged‑in user not found"));

        Restaurant restaurant = admin.getRestaurant();
        if (restaurant == null) {
            throw new IllegalStateException("Admin is not linked to any restaurant");
        }

        /* 3️⃣  Prevent duplicates inside *this* restaurant */
        if (categoryRepository.existsByRestaurantAndName(restaurant, dto.getName())) {
            throw new RuntimeException("Category already exists for this restaurant");
        }

        /* 4️⃣  Save category */
        Category category = new Category();
        category.setName(dto.getName());
        category.setRestaurant(restaurant);

        return CategoryMapper.toDTO(categoryRepository.save(category));
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Optional<Category> optional = categoryRepository.findById(id);
        if (optional.isEmpty()) return null;

        Category category = optional.get();
        category.setName(categoryDTO.getName());
        return CategoryMapper.toDTO(categoryRepository.save(category));
    }

    @Override
    public List<CategoryDTO> findCategoriesByAdminRestaurant(Long restaurantId) {
            return categoryRepository.findByRestaurantId(restaurantId)
                    .stream()
                    .map(CategoryMapper::toDTO)
                    .collect(Collectors.toList());
        }

    @Override
    public List<CategoryDTO> getCategoriesByRestaurantId(Long restaurantId) {
        return categoryRepository.findByRestaurantId(restaurantId)
                .stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toList());
    }
}
