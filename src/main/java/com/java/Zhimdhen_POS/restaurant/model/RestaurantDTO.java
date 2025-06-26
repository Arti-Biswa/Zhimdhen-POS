package com.java.Zhimdhen_POS.restaurant.model;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RestaurantDTO{
    private long id;
    private String restaurant_name;
    private String license_no;
    private String address;
    private String image;
    private String username;
    private String email;
    private String password; // <-- Must be exactly this
    private String phoneNumber;
}
