package com.java.Zhimdhen_POS.order.model;

import com.java.Zhimdhen_POS.restaurant.model.Restaurant;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "orders",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"restaurant_id", "order_number"}))
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long tableId;

    private LocalDateTime orderTime;

    private String status; // e.g. NEW, VIEWED, COMPLETED

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (orderTime == null) {
            orderTime = LocalDateTime.now();
        }
    }

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;



}
