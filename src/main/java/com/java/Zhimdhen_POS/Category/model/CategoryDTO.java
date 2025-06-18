package com.java.Zhimdhen_POS.Category.model;

public class CategoryDTO {
    private Long id;
    private String name;
    private String description;  // <<-- Add this field

    // Default constructor
    public CategoryDTO() {}

    // Constructor with all fields
    public CategoryDTO(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // Constructor without id
    public CategoryDTO(String name, String description) {
        this.name = name;
        this.description = description;
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

    public String getDescription() {      // getter for description
        return description;
    }

    public void setDescription(String description) {   // setter for description
        this.description = description;
    }
}
