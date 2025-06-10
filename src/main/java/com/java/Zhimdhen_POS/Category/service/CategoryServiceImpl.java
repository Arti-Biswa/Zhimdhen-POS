package com.java.Zhimdhen_POS.Category.service;

import com.java.Zhimdhen_POS.Category.mapper.CategoryMapper;
import com.java.Zhimdhen_POS.Category.model.Category;
import com.java.Zhimdhen_POS.Category.model.CategoryDTO;
import com.java.Zhimdhen_POS.Category.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

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
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        if (categoryRepository.findByName(categoryDTO.getName()).isPresent()) {
            throw new RuntimeException("Category already exists");
        }
        Category category = CategoryMapper.toEntity(categoryDTO);
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
}
