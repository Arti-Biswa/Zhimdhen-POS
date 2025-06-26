package com.java.Zhimdhen_POS.restaurant.services;

import com.java.Zhimdhen_POS.restaurant.mapper.RestaurantMapper;
import com.java.Zhimdhen_POS.restaurant.model.Restaurant;
import com.java.Zhimdhen_POS.restaurant.model.RestaurantDTO;
import com.java.Zhimdhen_POS.restaurant.repository.RestaurantRepository;
import com.java.Zhimdhen_POS.users.model.User;
import com.java.Zhimdhen_POS.users.repository.UserRepository;
import com.java.Zhimdhen_POS.utils.exception.GlobalExceptionWrapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.java.Zhimdhen_POS.utils.constants.UserConstants.*;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private RestaurantMapper restaurantMapper;

    @Override
    public RestaurantDTO save(RestaurantDTO dto, MultipartFile imageFile) throws IOException {

        // Save image to local folder or desired location
        String filename = imageFile.getOriginalFilename();
        Path imagePath = Paths.get("uploads/restaurants", filename);
        Files.createDirectories(imagePath.getParent());
        Files.copy(imageFile.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

        // Save just the filename or relative path in the DB
        dto.setImage("uploads/restaurants/" + filename);

        // Convert DTO to entity
        Restaurant restaurant = restaurantMapper.toRestaurant(dto);

        // Optional: Set user and role here if needed
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(Long.valueOf(dto.getPhoneNumber()));
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(User.Role.ADMIN); // Assign admin role
        userRepository.save(user);

        restaurant.setUser(user);

        restaurantRepository.save(restaurant);

        return restaurantMapper.toDto(restaurant);
    }

    @Override
    public RestaurantDTO fetchById(long id) {
        Restaurant restaurant = findById(id);
        return restaurantMapper.toDto(restaurant);
    }

    @Override
    public String update(long id, RestaurantDTO entity) {
        return "";
    }

    @Override
    public Restaurant findById(long id) {
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new GlobalExceptionWrapper.NotFoundException(
                        String.format(NOT_FOUND_MESSAGE, USER.toLowerCase())
                ));
    }

    @Override
    public List<RestaurantDTO> findAll() {
        List<Restaurant> allRestaurants = restaurantRepository.findAll();
        return RestaurantMapper.toDto(allRestaurants);
    }

    @Override
    public RestaurantDTO save(Restaurant entity) {
        return null;
    }

    @Override
    @Transactional
    public String update(long id, RestaurantDTO dto, MultipartFile imageFile) throws IOException {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));

        User user = restaurant.getUser();        // 1️⃣ linked user row

        /* ---------- Restaurant fields ---------- */
        Optional.ofNullable(dto.getRestaurant_name()).ifPresent(restaurant::setRestaurant_name);
        Optional.ofNullable(dto.getLicense_no()).ifPresent(restaurant::setLicense_no);
        Optional.ofNullable(dto.getAddress()).ifPresent(restaurant::setAddress);

        /* ---------- Image (if any) ------------- */
        if (imageFile != null && !imageFile.isEmpty()) {
            String filename  = UUID.randomUUID() + "_" + imageFile.getOriginalFilename(); // avoid collisions
            Path   imagePath = Paths.get("uploads", "restaurants", filename);
            Files.createDirectories(imagePath.getParent());
            Files.copy(imageFile.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

            restaurant.setImage(imagePath.toString().replace(File.separatorChar, '/'));
        }

        /* ---------- User fields ---------------- */
        Optional.ofNullable(dto.getUsername()).ifPresent(user::setUsername);
        Optional.ofNullable(dto.getEmail()).ifPresent(user::setEmail);
        Optional.ofNullable(dto.getPhoneNumber())
                .map(Long::valueOf)
                .ifPresent(user::setPhoneNumber);

        // 🔑  Hash the new password before saving
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            String hashed = passwordEncoder.encode(dto.getPassword());
            user.setPassword(hashed);
        }

        // Persist both; thanks to @Transactional we could even omit explicit saves
        userRepository.save(user);
        restaurantRepository.save(restaurant);

        return "Restaurant updated successfully.";
    }


    @Override
    public User fetchSelfInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = ((com.java.Zhimdhen_POS.auth.helper.UserInfoDetails) auth.getPrincipal()).getUsername();
        return findByEmail(email)
                .orElseThrow(() -> new GlobalExceptionWrapper.NotFoundException(
                        String.format(NOT_FOUND_MESSAGE, USER.toLowerCase())
                ));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Transactional
    @Override
    public String deleteById(long restaurantId) {
        User requester = fetchSelfInfo();

        if (requester.getRole() != User.Role.SUPER_ADMIN) {
            throw new AccessDeniedException("Only SUPER_ADMIN can delete restaurants.");
        }

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant not found"));

        User adminUser = restaurant.getUser(); // assuming you have a `getUser()` link

        // First delete the restaurant
        restaurantRepository.delete(restaurant);

        // Then delete the user who owned that restaurant
        if (adminUser != null) {
            userRepository.deleteById(adminUser.getId());
        }

        return String.format("Restaurant and its admin user deleted successfully.");
    }
}
