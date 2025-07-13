package com.java.Zhimdhen_POS.order.service;

import com.java.Zhimdhen_POS.order.mapper.OrderMapper;
import com.java.Zhimdhen_POS.order.model.Order;
import com.java.Zhimdhen_POS.order.model.OrderDTO;
import com.java.Zhimdhen_POS.order.model.OrderResponseDTO;
import com.java.Zhimdhen_POS.order.repository.OrderRepository;
import com.java.Zhimdhen_POS.product.mapper.ProductMapper;
import com.java.Zhimdhen_POS.product.model.ProductDTO;
import com.java.Zhimdhen_POS.restaurant.model.Restaurant;
import com.java.Zhimdhen_POS.restaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order placeOrder(OrderDTO orderDTO) {
        Restaurant restaurant = restaurantRepository.findById(orderDTO.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        Order order = OrderMapper.toEntity(orderDTO, restaurant); // ✅ pass it here

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
    public List<OrderResponseDTO> getOrdersByRestaurantId(Long restaurantId) {
        List<Order> orders = orderRepository.findByRestaurantId(restaurantId);
        return orders.stream()
                .map(OrderMapper::toResponse)
                .collect(Collectors.toList());    }

    @Override
    @Transactional
    public void markOrdersAsViewed(Long tableId) {
        orderRepository.markOrdersAsViewedByTableId(tableId);
    }

    @Override
    public Map<Long, Long> countNewOrdersByTableForRestaurant(Long restaurantId) {
        List<Order> orders = orderRepository.findByRestaurantIdAndStatus(restaurantId, "NEW");

        return orders.stream()
                .collect(Collectors.groupingBy(
                        Order::getTableId,
                        Collectors.counting()
                ));
    }

}
