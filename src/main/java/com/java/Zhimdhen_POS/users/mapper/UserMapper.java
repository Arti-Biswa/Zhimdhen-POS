package com.java.Zhimdhen_POS.users.mapper;

import com.java.Zhimdhen_POS.users.model.User;
import com.java.Zhimdhen_POS.users.model.UserDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    /**
     * Maps the user to user dto.
     *
     * @param user The user entity.
     * @return Returns the user entity.
     */
    public static UserDTO toDTO(User user) {
        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(user, dto, "password");
        dto.setRole(String.valueOf(user.getRole()));
        return dto;
    }

    /**
     * Maps the list of users to user dto
     *
     * @param users The list of user entity
     * @return The list of user dto.
     */
    public static List<UserDTO> toDTO(List<User> users) {
        return users.stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Maps the optional user to optional user dto.
     *
     * @param user The user entity
     * @return The optional user dto.
     */
    public static Optional<UserDTO> toDTO(Optional<User> user) {
        return user.map(UserMapper::toDTO);
    }

    /**
     * Maps the user dto  to the user entity.
     *
     * @param dto The user dto.
     * @return The user entity.
     */
    public static User toEntity(User dto) {
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        return user;
    }
}
