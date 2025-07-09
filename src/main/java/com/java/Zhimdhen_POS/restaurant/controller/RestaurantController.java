package com.java.Zhimdhen_POS.restaurant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.Zhimdhen_POS.restaurant.model.RestaurantDTO;
import com.java.Zhimdhen_POS.restaurant.services.RestaurantServiceImpl;
import com.java.Zhimdhen_POS.utils.RestHelper;
import com.java.Zhimdhen_POS.utils.RestResponse;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantServiceImpl restaurantServiceImpl;

    /**
     * Signing up the new restaurant.
     *
     * @return The saved entity.
     */

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<?> save(
            @RequestPart("restaurant") String restaurantJson,
            @RequestPart("imageFile") MultipartFile imageFile) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            RestaurantDTO restaurantDTO = mapper.readValue(restaurantJson, RestaurantDTO.class);

            RestaurantDTO savedRestaurant = restaurantServiceImpl.save(restaurantDTO, imageFile);

            return new ResponseEntity<>(savedRestaurant, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /** Returns the restaurant self info **/
    @GetMapping("/self")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RestResponse> fetchSelfRestaurant() {
        RestaurantDTO dto = restaurantServiceImpl.fetchSelfRestaurant();
        HashMap<String, Object> map = new HashMap<>();
        map.put("restaurant", dto);
        return RestHelper.responseSuccess(map);
    }

    /**
     * Fetches the restaurant by identifier.
     *
     * @param id The unique identifier of the user.
     * @return The user entity.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<RestResponse> findById(@PathVariable long id) {
        HashMap<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("restaurant", restaurantServiceImpl.fetchById(id));
        return RestHelper.responseSuccess(listHashMap);
    }

    /**
     * Fetches all the user entities in the system.
     *
     * @return The list of user entities.
     */
    @GetMapping
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<RestResponse> findAllRestaurants() {
        HashMap<String, Object> response = new HashMap<>();
        response.put("restaurants", restaurantServiceImpl.findAll()); // or whatever your service is called
        return RestHelper.responseSuccess(response);
    }

    /**
     * Deletes the restaurant by id.
     *
     * @param id The unique identifier of the entity.
     * @return The message indicating the confirmation on deleted restaurant entity.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<RestResponse> delete(@PathVariable long id) {
        String message = restaurantServiceImpl.deleteById(id);
        return RestHelper.responseMessage(message);
    }
    /**
     * Updates the existing restaurant entity.
     *
     * @param id The updated restaurant entity.
     * @return The message indicating the confirmation on updated restaurant entity.
     */

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    public ResponseEntity<RestResponse> update(
            @PathVariable long id,
            @RequestPart("restaurant") String restaurantJson,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            RestaurantDTO restaurantDTO = mapper.readValue(restaurantJson, RestaurantDTO.class);

            String message = restaurantServiceImpl.update(id, restaurantDTO, imageFile);
            return RestHelper.responseMessage(message);
        } catch (Exception e) {
            return RestHelper.responseMessage("Update failed: " + e.getMessage());
        }
    }

}
