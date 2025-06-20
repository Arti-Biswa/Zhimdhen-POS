package com.java.Zhimdhen_POS.product.model;

import com.java.Zhimdhen_POS.Category.model.Category;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "product")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private double price;

    @Column
    private String image;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

   }
