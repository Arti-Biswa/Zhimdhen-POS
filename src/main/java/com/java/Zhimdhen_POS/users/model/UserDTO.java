package com.java.Zhimdhen_POS.users.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO{
    private long id;
    private String username;
    private String email;
    private String password;
    private Long PhoneNumber;
    private String role;
}
