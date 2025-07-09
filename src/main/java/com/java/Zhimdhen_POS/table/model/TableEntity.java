package com.java.Zhimdhen_POS.table.model;

import com.java.Zhimdhen_POS.restaurant.model.Restaurant;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(
        name = "restaurant_tables",
        uniqueConstraints = @UniqueConstraint(columnNames = {"restaurant_id", "table_number"})
)
public class TableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "table_number", nullable = false, unique = true)
    private String tableNumber;

    @ManyToOne(optional = false)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;


}