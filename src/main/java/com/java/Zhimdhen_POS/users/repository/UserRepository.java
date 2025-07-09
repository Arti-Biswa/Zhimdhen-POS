package com.java.Zhimdhen_POS.users.repository;

import com.java.Zhimdhen_POS.restaurant.model.Restaurant;
import com.java.Zhimdhen_POS.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    List<User>findByRole(User.Role role);

    List<User> findByRestaurant(Restaurant restaurant);

    Optional<User> findByEmailIgnoreCase(String email);

}
