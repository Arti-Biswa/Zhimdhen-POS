package com.java.Zhimdhen_POS.dailysalessummary.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailySalesSummaryDTO {
    private LocalDate saleDate;
    private String itemName;
    private int quantitySold;
    private double totalAmount;
}
