package com.java.Zhimdhen_POS.product.model;

import com.java.Zhimdhen_POS.Category.model.Category;
import com.java.Zhimdhen_POS.restaurant.model.Restaurant;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(
        name = "product",
        uniqueConstraints = @UniqueConstraint(columnNames = {"restaurant_id", "name"})
)
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private double price;

    @Column(nullable = false)
    private String image;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

   }
