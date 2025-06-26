package com.java.Zhimdhen_POS.restaurant.services;

import com.java.Zhimdhen_POS.restaurant.model.Restaurant;
import com.java.Zhimdhen_POS.restaurant.model.RestaurantDTO;
import com.java.Zhimdhen_POS.users.model.User;
import com.java.Zhimdhen_POS.utils.IGenericCrudService;
import jakarta.transaction.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;


public interface RestaurantService extends IGenericCrudService<Restaurant, RestaurantDTO>{

    RestaurantDTO save(RestaurantDTO dto, MultipartFile imageFile) throws IOException;

    Restaurant findById(long id);

    @Transactional
    String update(long id, RestaurantDTO dto, MultipartFile imageFile) throws IOException;

    User fetchSelfInfo();

    Optional<User> findByEmail(String email);

    @Transactional
    String deleteById(long restaurantId);
}
