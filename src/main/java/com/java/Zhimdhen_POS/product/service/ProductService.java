package com.java.Zhimdhen_POS.product.service;

import com.java.Zhimdhen_POS.product.model.ProductDTO;

import java.util.List;

public interface ProductService {

    ProductDTO createProduct(ProductDTO productDTO);

    ProductDTO updateProduct(Long productId, ProductDTO productDTO); // ✅ for PUT request

    void deleteProduct(Long productId); // ✅ for DELETE request

    List<ProductDTO> getProductsByCategoryId(Long categoryId);
}
