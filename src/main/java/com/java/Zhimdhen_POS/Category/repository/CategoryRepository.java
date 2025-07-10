package com.java.Zhimdhen_POS.Category.repository;

import com.java.Zhimdhen_POS.Category.model.Category;
import com.java.Zhimdhen_POS.product.model.Product;
import com.java.Zhimdhen_POS.restaurant.model.Restaurant;
import com.java.Zhimdhen_POS.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByName(String name);

    boolean existsByRestaurantAndName(Restaurant restaurant, String name);

    List<Category> findByRestaurantId(Long restaurantId);

}
