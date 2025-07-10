package com.java.Zhimdhen_POS.table.repository;

import com.java.Zhimdhen_POS.restaurant.model.Restaurant;
import com.java.Zhimdhen_POS.table.model.TableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<TableEntity, Long> {
    boolean existsByTableNumber(String tableNumber);

    boolean existsByRestaurantAndTableNumber(Restaurant restaurant, String tableNumber);

    List<TableEntity> findByRestaurantId(Long restaurantId);

}
