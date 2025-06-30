package com.java.Zhimdhen_POS.order.mapper;


import com.java.Zhimdhen_POS.order.model.Order;
import com.java.Zhimdhen_POS.order.model.OrderResponseDTO;
import com.java.Zhimdhen_POS.product.model.Product;
import com.java.Zhimdhen_POS.product.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CustomOrderMapper {
    private final ProductRepository productRepository;

    public CustomOrderMapper(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public OrderResponseDTO toDetailedResponse(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        dto.setTableId(order.getTableId());
        dto.setOrderTime(order.getOrderTime());
        dto.setStatus(order.getStatus());

        var itemDTOs = order.getItems().stream().map(item -> {
            OrderResponseDTO.OrderItemResponseDTO itemDto = new OrderResponseDTO.OrderItemResponseDTO();
            itemDto.setId(item.getId());
            itemDto.setProductId(item.getProductId());
            itemDto.setQuantity(item.getQuantity());

            // 🟢 Fetch product details
            Product product = productRepository.findById(item.getProductId()).orElse(null);
            if (product != null) {
                itemDto.setItemName(product.getName());
                itemDto.setPrice(product.getPrice());
                itemDto.setImageUrl(product.getImage());
            }

            return itemDto;
        }).collect(Collectors.toList());

        dto.setItems(itemDTOs);
        return dto;
    }
}
