package com.java.Zhimdhen_POS.product.mapper;

import com.java.Zhimdhen_POS.Category.model.Category;
import com.java.Zhimdhen_POS.product.model.Product;
import com.java.Zhimdhen_POS.product.model.ProductDTO;

public class ProductMapper {

    public static ProductDTO toDTO(Product product) {
        if (product == null) return null;

        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setImage(product.getImage()); // ✅ Include image path

        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getId());
        }

        return dto;
    }

    public static Product toEntity(ProductDTO dto, Category category) {
        if (dto == null) return null;

        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setCategory(category);
        product.setImage(dto.getImage()); // ✅ Include image path

        return product;
    }
}
