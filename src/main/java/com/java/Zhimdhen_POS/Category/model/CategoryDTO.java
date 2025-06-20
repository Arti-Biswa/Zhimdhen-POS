package com.java.Zhimdhen_POS.Category.model;

public class CategoryDTO {
    private Long id;
    private String name;


    // Default constructor
    public CategoryDTO() {}

    // Constructor with all fields
    public CategoryDTO(Long id, String name) {
        this.id = id;
        this.name = name;

    }

    // Constructor without id
    public CategoryDTO(String name, String description) {
        this.name = name;

    }

    // Getters and setters for all fields

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
