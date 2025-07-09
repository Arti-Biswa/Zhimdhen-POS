package com.java.Zhimdhen_POS.Category.mapper;

import com.java.Zhimdhen_POS.Category.model.Category;
import com.java.Zhimdhen_POS.Category.model.CategoryDTO;
import com.java.Zhimdhen_POS.restaurant.model.Restaurant;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {
    public static CategoryDTO toDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());

        if (category.getRestaurant() != null) {
            dto.setRestaurantId(category.getRestaurant().getId());
        }

        return dto;
    }

    public static Category toEntity(CategoryDTO dto) {
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());

        if (dto.getRestaurantId() != null) {             // read from *DTO*
            Restaurant r = new Restaurant();             // a stub with just the ID
            r.setId(dto.getRestaurantId());
            category.setRestaurant(r);                   // attach it to the entity
        }

        return category;
    }
    public static List<CategoryDTO> toDTO(List<Category> categories) {
        return categories.stream()
                .map(CategoryMapper::toDTO)
                .collect(Collectors.toList());
    }
}
