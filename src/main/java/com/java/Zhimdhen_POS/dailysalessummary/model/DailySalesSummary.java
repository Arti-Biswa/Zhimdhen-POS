package com.java.Zhimdhen_POS.dailysalessummary.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "daily_sales_summary")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailySalesSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate saleDate;
    private String itemName;
    private int quantity;
    private double totalAmount;
}
