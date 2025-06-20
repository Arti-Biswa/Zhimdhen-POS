package com.java.Zhimdhen_POS.product.model;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private double price;
    private Long categoryId;
    private String image;
}
