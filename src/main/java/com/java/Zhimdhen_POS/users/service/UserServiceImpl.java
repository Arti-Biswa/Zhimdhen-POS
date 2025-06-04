package com.java.Zhimdhen_POS.users.service;

import com.java.Zhimdhen_POS.users.mapper.UserMapper;
import com.java.Zhimdhen_POS.users.model.User;
import com.java.Zhimdhen_POS.users.model.UserDTO;
import com.java.Zhimdhen_POS.users.repository.UserRepository;
import com.java.Zhimdhen_POS.utils.exception.GlobalExceptionWrapper;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.java.Zhimdhen_POS.utils.constants.UserConstants.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private  UserRepository userRepository;

    @Override
    public UserDTO save(@NonNull User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new GlobalExceptionWrapper.BadRequestException(DUPLICATE_EMAIL_MESSAGE);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);
        return UserMapper.toDTO(savedUser);
    }

    @Override
    public List<UserDTO> findAll() {
        List<User> allUsers = userRepository.findAll();
        return UserMapper.toDTO(allUsers);
    }

    @Override
    public UserDTO fetchById(long id) {
        User user = findById(id);
        return UserMapper.toDTO(user);
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new GlobalExceptionWrapper.NotFoundException(
                        String.format(NOT_FOUND_MESSAGE, USER.toLowerCase())
                ));
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

    @Override
    public String update (long id, UserDTO dto){
        User user = userRepository.findById(id)
        .orElseThrow(()->new RuntimeException("User not found"));

        if(dto.getUsername()!= null)user.setUsername(dto.getUsername());
        if(dto.getPhoneNumber()!= null)user.setPhoneNumber(dto.getPhoneNumber());

        userRepository.save(user);
        return "User Updated succesfully";

    }
    @Override
    public String updateEntity(User user) {
        userRepository.save(user);
        return String.format(UPDATED_SUCCESSFULLY_MESSAGE, USER);
    }

    @Override
    @Transactional
    public String deleteById(long id) {
        User authenticatedUser = fetchSelfInfo();

        boolean isAdmin = Arrays.stream(authenticatedUser.getRole().name().split(","))
                .anyMatch(r -> r.trim().equalsIgnoreCase("ADMIN"));

        if (isAdmin) {
            authenticatedUser = findById(id);
        }

        userRepository.deleteById(authenticatedUser.getId());
        return String.format(DELETED_SUCCESSFULLY_MESSAGE, USER);
    }
}
