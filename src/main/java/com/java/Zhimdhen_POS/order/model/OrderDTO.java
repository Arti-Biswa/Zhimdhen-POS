package com.java.Zhimdhen_POS.order.model;

import lombok.Data;
import java.util.List;

@Data
public class OrderDTO {
    private Long tableId;
    private List<ItemDTO> items;

    @Data
    public static class ItemDTO {
        private Long productId;
        private int quantity;
    }
}
