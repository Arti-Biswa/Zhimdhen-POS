package com.java.Zhimdhen_POS.restaurant.repository;

import com.java.Zhimdhen_POS.restaurant.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {

    Optional<Restaurant> findByUser_Email(String email);
}
