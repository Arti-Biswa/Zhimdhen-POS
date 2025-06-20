package com.java.Zhimdhen_POS.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.Zhimdhen_POS.product.model.ProductDTO;
import com.java.Zhimdhen_POS.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // 🔒 Admin only: Add product with image upload
    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> save(
            @RequestPart("product") String productJson,
            @RequestPart("imageFile") MultipartFile imageFile) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ProductDTO productDTO = mapper.readValue(productJson, ProductDTO.class);
            ProductDTO savedProduct = productService.createProduct(productDTO, imageFile);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 🔒 Admin only: Update product
    @PutMapping(value = "/{productId}", consumes = {"multipart/form-data"})
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> updateProduct(
            @PathVariable Long productId,
            @RequestPart("product") String productJson,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ProductDTO productDTO = mapper.readValue(productJson, ProductDTO.class);
            ProductDTO updatedProduct = productService.updateProduct(productId, productDTO, imageFile);
            return ResponseEntity.ok(updatedProduct);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // 🔒 Admin only: Delete product
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok("✅ Product deleted successfully!");
    }

    // ❗ Temporarily accessible here, but should ideally be in a public controller
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.getProductsByCategoryId(categoryId));
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

}
