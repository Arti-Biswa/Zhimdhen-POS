package com.java.Zhimdhen_POS.order.repository;
import com.java.Zhimdhen_POS.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByTableId(Long tableId);

    // Count NEW orders grouped by tableId
    @Query("SELECT o.tableId AS tableId, COUNT(o) AS count FROM Order o WHERE o.status = 'NEW' GROUP BY o.tableId")
    List<Object[]> countNewOrdersGroupedByTable();

    // Mark orders as VIEWED for a given tableId
    @Modifying
    @Transactional
    @Query("UPDATE Order o SET o.status = 'VIEWED' WHERE o.tableId = :tableId AND o.status = 'NEW'")
    int markOrdersAsViewedByTableId(Long tableId);

    // ✅ NEW: Fetch the latest order with its items for a specific table
    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.items WHERE o.tableId = :tableId ORDER BY o.orderTime DESC")
    List<Order> findTopByTableIdOrderByOrderTimeDesc(@Param("tableId") Long tableId);

}
