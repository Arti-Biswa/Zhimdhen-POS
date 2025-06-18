package com.java.Zhimdhen_POS.product.mapper;

import com.java.Zhimdhen_POS.Category.model.Category;
import com.java.Zhimdhen_POS.product.model.Product;
import com.java.Zhimdhen_POS.product.model.ProductDTO;

import java.util.Base64;

public class ProductMapper {

    public static ProductDTO toDTO(Product product) {
        if (product == null) return null;

        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());

        if (product.getImage() != null) {
            dto.setImage(Base64.getEncoder().encodeToString(product.getImage()));
        }

        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getId());
        }

        return dto;
    }

    public static Product toEntity(ProductDTO dto, Category category) {
        if (dto == null) return null;

        Product product = new Product();
        product.setId(dto.getId());  // optional, usually null for new products
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());

        if (dto.getImage() != null && !dto.getImage().isEmpty()) {
            String base64Image = dto.getImage();

            // If image string contains "data:image/...", remove the prefix before decoding
            if (base64Image.contains(",")) {
                base64Image = base64Image.substring(base64Image.indexOf(",") + 1);
            }

            base64Image = base64Image.trim().replaceAll("\\s+", "");
            product.setImage(Base64.getDecoder().decode(base64Image));
        }

        product.setCategory(category);

        return product;
    }
}
