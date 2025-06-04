package com.java.Zhimdhen_POS.users.service;

import com.java.Zhimdhen_POS.users.model.User;
import com.java.Zhimdhen_POS.users.model.UserDTO;
import com.java.Zhimdhen_POS.utils.IGenericCrudService;

import java.util.Optional;


public interface UserService extends IGenericCrudService<User, UserDTO>{
    User findById(long id);

    User fetchSelfInfo();

    Optional<User> findByEmail(String email);

    String updateEntity(User user);
}
