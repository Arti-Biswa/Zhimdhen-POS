package com.java.Zhimdhen_POS.order.service;

import com.java.Zhimdhen_POS.order.model.Order;
import com.java.Zhimdhen_POS.order.model.OrderDTO;

import java.util.List;
import java.util.Map;

public interface OrderService {
    Order placeOrder(OrderDTO orderDTO);
    List<Order> getOrdersByTable(Long tableId);

    // Returns a map of tableId -> count of new orders for notification badge
    Map<Long, Long> countNewOrdersByTable();

    // Marks orders as viewed for a given tableId (clears notification)
    void markOrdersAsViewed(Long tableId);
}
