package com.java.Zhimdhen_POS.order.controller;


import com.java.Zhimdhen_POS.auth.helper.UserInfoDetails;
import com.java.Zhimdhen_POS.order.mapper.CustomOrderMapper;
import com.java.Zhimdhen_POS.order.mapper.OrderMapper;
import com.java.Zhimdhen_POS.order.model.Order;
import com.java.Zhimdhen_POS.order.model.OrderDTO;
import com.java.Zhimdhen_POS.order.model.OrderResponseDTO;
import com.java.Zhimdhen_POS.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin
public class OrderController {
    private final OrderService orderService;
    private final CustomOrderMapper customOrderMapper;

    public OrderController(OrderService orderService,CustomOrderMapper customOrderMapper) {
        this.orderService = orderService;
        this.customOrderMapper = customOrderMapper;
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
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CASHIER')")
    public Map<Long, Long> getMyNewOrdersCount(@AuthenticationPrincipal UserInfoDetails me) {
        return orderService.countNewOrdersByTableForRestaurant(me.getRestaurantId());
    }


    @PostMapping("/mark-viewed/{tableId}")
    public void markOrdersAsViewed(@PathVariable Long tableId) {
        orderService.markOrdersAsViewed(tableId);
    }

    @GetMapping("/latest/{tableId}")
    public OrderResponseDTO getLatestOrder(@PathVariable Long tableId) {
        Order order = orderService.getLatestOrderWithItems(tableId);
        if (order == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No order found for table " + tableId);
        }
        return customOrderMapper.toDetailedResponse(order);
    }

    @GetMapping("/by-restaurant")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'CASHIER')")
    public List<OrderResponseDTO> listOrdersForMyRestaurant(@AuthenticationPrincipal UserInfoDetails me) {
        return orderService.getOrdersByRestaurantId(me.getRestaurantId());
    }


}
