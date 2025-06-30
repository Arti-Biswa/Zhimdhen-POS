package com.java.Zhimdhen_POS.order.model;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDTO {
    private Long id;
    private Long tableId;
    private LocalDateTime orderTime;
    private String status;
    private List<OrderItemResponseDTO> items;

    @Data
    public static class OrderItemResponseDTO {
        private Long id;
        private Long productId;
        private int quantity;
        private String itemName;      // ✅ NEW
        private Double price;         // ✅ NEW
        private String imageUrl;
    }
}
