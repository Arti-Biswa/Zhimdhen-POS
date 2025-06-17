package com.java.Zhimdhen_POS.table.model;

import jakarta.persistence.*;
@Entity
@Table(name = "restaurant_tables")
public class TableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "table_number", nullable = false, unique = true)
    private String tableNumber;

    // Constructors
    public TableEntity() {}
    public TableEntity(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }
}