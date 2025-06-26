package com.java.Zhimdhen_POS.order.controller;


import com.java.Zhimdhen_POS.order.mapper.OrderMapper;
import com.java.Zhimdhen_POS.order.model.Order;
import com.java.Zhimdhen_POS.order.model.OrderDTO;
import com.java.Zhimdhen_POS.order.model.OrderResponseDTO;
import com.java.Zhimdhen_POS.order.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public OrderResponseDTO placeOrder(@RequestBody OrderDTO orderDTO) {
        Order savedOrder = orderService.placeOrder(orderDTO);
        return OrderMapper.toResponse(savedOrder);
    }

    @GetMapping("/table/{tableId}")
    public List<OrderResponseDTO> getOrdersByTable(@PathVariable Long tableId) {
        List<Order> orders = orderService.getOrdersByTable(tableId);
        return orders.stream()
                .map(OrderMapper::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/new-count")
    public Map<Long, Long> getNewOrdersCount() {
        return orderService.countNewOrdersByTable();
    }

    @PostMapping("/mark-viewed/{tableId}")
    public void markOrdersAsViewed(@PathVariable Long tableId) {
        orderService.markOrdersAsViewed(tableId);
    }
}
