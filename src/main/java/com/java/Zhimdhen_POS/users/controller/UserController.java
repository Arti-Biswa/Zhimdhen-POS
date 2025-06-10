package com.java.Zhimdhen_POS.users.controller;

import com.java.Zhimdhen_POS.users.model.User;
import com.java.Zhimdhen_POS.users.model.UserDTO;
import com.java.Zhimdhen_POS.users.service.UserServiceImpl;
import com.java.Zhimdhen_POS.utils.RestHelper;
import com.java.Zhimdhen_POS.utils.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping("/self")
    @PreAuthorize("hasAnyAuthority('CASHIER','ADMIN')")
    public ResponseEntity<RestResponse> fetchSelfInfo() {
        HashMap<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("user", userServiceImpl.fetchSelfInfo());
        return RestHelper.responseSuccess(listHashMap);
    }

    /**
     * Fetches the user by identifier.
     *
     * @param id The unique identifier of the user.
     * @return The user entity.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RestResponse> findById(@PathVariable long id) {
        HashMap<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("user", userServiceImpl.fetchById(id));
        return RestHelper.responseSuccess(listHashMap);
    }

    /**
     * Fetches all the user entities in the system.
     *
     * @return The list of user entities.
     */
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RestResponse> findAll() {
        HashMap<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("users", userServiceImpl.findAll());
        return RestHelper.responseSuccess(listHashMap);
    }

    /**
     * Signing up the new user.
     *
     * @param user The entity to be saved.
     * @return The saved entity.
     */
    @PostMapping
    public ResponseEntity<RestResponse> save(@Validated @RequestBody User user) {
        HashMap<String, Object> listHashMap = new HashMap<>();
        listHashMap.put("user", userServiceImpl.save(user));
        return RestHelper.responseSuccess(listHashMap);
    }

    /**
     * Deletes the user by id.
     *
     * @param id The unique identifier of the entity.
     * @return The message indicating the confirmation on deleted user entity.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<RestResponse> delete(@PathVariable long id) {
        String message = userServiceImpl.deleteById(id);
        return RestHelper.responseMessage(message);
    }

    /**
     * Updates the existing user entity.
     *
     * @param id The updated user entity.
     * @return The message indicating the confirmation on updated user entity.
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ResponseEntity<RestResponse> update(@PathVariable long id, @Validated UserDTO UserDTO) {

        String message = userServiceImpl.update(id, UserDTO);

        return RestHelper.responseMessage(message);
    }
}
