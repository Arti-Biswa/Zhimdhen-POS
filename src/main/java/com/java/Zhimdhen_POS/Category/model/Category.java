package com.java.Zhimdhen_POS.Category.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.java.Zhimdhen_POS.restaurant.model.Restaurant;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(
        name = "Category",
        uniqueConstraints = @UniqueConstraint(columnNames = {"restaurant_id","name"})
)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public Category() {}

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
