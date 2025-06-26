package com.java.Zhimdhen_POS.restaurant.mapper;

import com.java.Zhimdhen_POS.restaurant.model.Restaurant;
import com.java.Zhimdhen_POS.restaurant.model.RestaurantDTO;
import com.java.Zhimdhen_POS.users.mapper.UserMapper;
import com.java.Zhimdhen_POS.users.model.User;
import com.java.Zhimdhen_POS.users.model.UserDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestaurantMapper {

    // Convert DTO → User (owner)
    public User toUser(RestaurantDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword()); // NOTE: Encrypt in service
        user.setPhoneNumber(Long.valueOf(dto.getPhoneNumber()));
        user.setRole(User.Role.ADMIN); // Always assign role as ADMIN
        return user;
    }

    // Convert DTO → Restaurant (without user yet)
    public Restaurant toRestaurant(RestaurantDTO dto) {
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurant_name(dto.getRestaurant_name());
        restaurant.setLicense_no(dto.getLicense_no());
        restaurant.setAddress(dto.getAddress());
        restaurant.setImage(dto.getImage());
        return restaurant;
    }

    // Convert Restaurant + User to DTO (response)
    public static RestaurantDTO toDto(Restaurant restaurant) {
        RestaurantDTO dto = new RestaurantDTO();
        dto.setId(restaurant.getId());
        dto.setRestaurant_name(restaurant.getRestaurant_name());
        dto.setLicense_no(restaurant.getLicense_no());
        dto.setAddress(restaurant.getAddress());
        dto.setImage(restaurant.getImage());
        dto.setUsername(restaurant.getUser().getUsername());
        dto.setEmail(restaurant.getUser().getEmail());
        dto.setPhoneNumber(String.valueOf(restaurant.getUser().getPhoneNumber()));
        return dto;
    }


    public static List<RestaurantDTO> toDto(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(RestaurantMapper::toDto) // ✅ Now correct
                .collect(Collectors.toList());
    }

}
