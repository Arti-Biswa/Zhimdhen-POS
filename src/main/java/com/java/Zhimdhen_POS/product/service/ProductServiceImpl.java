package com.java.Zhimdhen_POS.product.service;

import com.java.Zhimdhen_POS.Category.model.Category;
import com.java.Zhimdhen_POS.Category.repository.CategoryRepository;
import com.java.Zhimdhen_POS.product.mapper.ProductMapper;
import com.java.Zhimdhen_POS.product.model.Product;
import com.java.Zhimdhen_POS.product.model.ProductDTO;
import com.java.Zhimdhen_POS.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO, MultipartFile imageFile) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());

        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        product.setCategory(category);

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                // Declare uploadDir here as a String
                String uploadDir = System.getProperty("user.dir") + "/uploads/images/";
                String filename = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();

                File saveFile = new File(uploadDir + filename);
                saveFile.getParentFile().mkdirs();
                imageFile.transferTo(saveFile);

                product.setImage("/uploads/images/" + filename);
            } catch (IOException e) {
                e.printStackTrace();  // Print the full stack trace
                throw new RuntimeException("Failed to save image", e);
            }
        }

        Product savedProduct = productRepository.save(product);

        return ProductMapper.toDTO(savedProduct);
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Skip category update if you don't want to change it
        existingProduct.setName(productDTO.getName());
        existingProduct.setPrice(productDTO.getPrice());

        Product updatedProduct = productRepository.save(existingProduct);
        return ProductMapper.toDTO(updatedProduct);
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO, MultipartFile imageFile) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Skip category update again
        existingProduct.setName(productDTO.getName());
        existingProduct.setPrice(productDTO.getPrice());

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String uploadDir = System.getProperty("user.dir") + "/uploads/images/";
                String filename = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();

                File saveFile = new File(uploadDir + filename);
                saveFile.getParentFile().mkdirs();
                imageFile.transferTo(saveFile);

                existingProduct.setImage("/uploads/images/" + filename);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to save image", e);
            }
        }

        Product updatedProduct = productRepository.save(existingProduct);
        return ProductMapper.toDTO(updatedProduct);
    }


    @Override
    public void deleteProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new RuntimeException("Product not found");
        }
        productRepository.deleteById(productId);
    }

    @Override
    public List<ProductDTO> getProductsByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId)
                .stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }

}
