package com.java.Zhimdhen_POS.product.mapper;

import com.java.Zhimdhen_POS.Category.model.Category;
import com.java.Zhimdhen_POS.product.model.Product;
import com.java.Zhimdhen_POS.product.model.ProductDTO;

public class ProductMapper {
    public static ProductDTO toDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setImage(product.getImage());
        dto.setCategoryId(product.getCategory().getId());
        return dto;
    }

    public static Product toEntity(ProductDTO dto, Category category) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setImage(dto.getImage());
        product.setCategory(category);
        return product;
    }
}
