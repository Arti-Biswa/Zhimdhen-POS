package com.java.Zhimdhen_POS.users.mapper;

import com.java.Zhimdhen_POS.restaurant.model.Restaurant;
import com.java.Zhimdhen_POS.restaurant.repository.RestaurantRepository;
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
        BeanUtils.copyProperties(user, dto, "password", "restaurant"); // exclude entity ref
        dto.setRole(String.valueOf(user.getRole()));

        // add this line
        dto.setRestaurantId(user.getRestaurant() != null
                ? user.getRestaurant().getId()
                : null);

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
    public static User toEntity(UserDTO dto, RestaurantRepository restRepo) {
        User user = new User();
        BeanUtils.copyProperties(dto, user, "restaurantId"); // ignore plain ID

        if (dto.getRestaurantId() != null) {
            Restaurant rest = restRepo.getReferenceById(dto.getRestaurantId());
            user.setRestaurant(rest);
        }

        return user;
    }

}
