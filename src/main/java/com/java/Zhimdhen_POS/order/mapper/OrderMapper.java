package com.java.Zhimdhen_POS.order.mapper;

import com.java.Zhimdhen_POS.order.model.Order;
import com.java.Zhimdhen_POS.order.model.OrderDTO;
import com.java.Zhimdhen_POS.order.model.OrderItem;
import com.java.Zhimdhen_POS.order.model.OrderResponseDTO;
import com.java.Zhimdhen_POS.restaurant.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {


    public static Order toEntity(OrderDTO orderDTO, Restaurant restaurant) {
        Order order = new Order();

        order.setTableId(orderDTO.getTableId());
        order.setRestaurant(restaurant);
        order.setOrderTime(LocalDateTime.now());
        order.setStatus("NEW");  // changed from PENDING to NEW

        List<OrderItem> items = orderDTO.getItems().stream()
                .map(itemDTO -> {
                    OrderItem item = new OrderItem();
                    item.setProductId(itemDTO.getProductId());
                    item.setQuantity(itemDTO.getQuantity());
                    item.setOrder(order);
                    return item;
                }).collect(Collectors.toList());

        order.setItems(items);

        return order;
    }

    public static OrderResponseDTO toResponse(Order order) {
        OrderResponseDTO response = new OrderResponseDTO();

        response.setId(order.getId());
        response.setTableId(order.getTableId());
        response.setOrderTime(order.getOrderTime());
        response.setStatus(order.getStatus());

        List<OrderResponseDTO.OrderItemResponseDTO> itemResponses = order.getItems().stream()
                .map(item -> {
                    OrderResponseDTO.OrderItemResponseDTO itemResponse = new OrderResponseDTO.OrderItemResponseDTO();
                    itemResponse.setId(item.getId());
                    itemResponse.setProductId(item.getProductId());
                    itemResponse.setQuantity(item.getQuantity());
                    return itemResponse;
                }).collect(Collectors.toList());

        response.setItems(itemResponses);

        return response;
    }
}
