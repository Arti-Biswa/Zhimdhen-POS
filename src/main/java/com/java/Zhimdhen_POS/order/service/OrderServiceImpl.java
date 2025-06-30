package com.java.Zhimdhen_POS.order.service;

import com.java.Zhimdhen_POS.order.mapper.OrderMapper;
import com.java.Zhimdhen_POS.order.model.Order;
import com.java.Zhimdhen_POS.order.model.OrderDTO;
import com.java.Zhimdhen_POS.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order placeOrder(OrderDTO orderDTO) {
        Order order = OrderMapper.toEntity(orderDTO);
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getOrdersByTable(Long tableId) {
        return orderRepository.findByTableId(tableId);
    }

    @Override
    public Map<Long, Long> countNewOrdersByTable() {
        List<Object[]> counts = orderRepository.countNewOrdersGroupedByTable();
        Map<Long, Long> result = new HashMap<>();
        for (Object[] row : counts) {
            Long tableId = (Long) row[0];
            Long count = (Long) row[1];
            result.put(tableId, count);
        }
        return result;
    }
    @Override
    public Order getLatestOrderWithItems(Long tableId) {
        List<Order> orders = orderRepository.findTopByTableIdOrderByOrderTimeDesc(tableId);
        return orders.isEmpty() ? null : orders.get(0);
    }

    @Override
    @Transactional
    public void markOrdersAsViewed(Long tableId) {
        orderRepository.markOrdersAsViewedByTableId(tableId);
    }
}
