package com.java.Zhimdhen_POS.product.model;

public class ProductDTO {
    private Long id;
    private String name;
    private double price;
    private byte[] image;
    private Long categoryId;

    // Constructors
    public ProductDTO() {}

    public ProductDTO(Long id, String name, double price, byte[] image, Long categoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.categoryId = categoryId;
    }

    // ✅ Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {   // <-- This method must exist
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    }
