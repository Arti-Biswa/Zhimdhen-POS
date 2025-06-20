package com.java.Zhimdhen_POS.product.service;

import com.java.Zhimdhen_POS.product.model.ProductDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {

    // Create product with image
    ProductDTO createProduct(ProductDTO productDTO, MultipartFile imageFile);

    // Update product by ID (without image)
    ProductDTO updateProduct(Long productId, ProductDTO productDTO);

    // Update product by ID with image (new method)
    ProductDTO updateProduct(Long productId, ProductDTO productDTO, MultipartFile imageFile);

    // Delete product by ID
    void deleteProduct(Long productId);

    // Get products filtered by category ID
    List<ProductDTO> getProductsByCategoryId(Long categoryId);

    // Get all products (NEW method)
    List<ProductDTO> getAllProducts();
}
