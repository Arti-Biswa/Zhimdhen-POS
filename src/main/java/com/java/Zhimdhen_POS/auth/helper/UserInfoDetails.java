package com.java.Zhimdhen_POS.auth.helper;

import com.java.Zhimdhen_POS.users.model.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserInfoDetails implements UserDetails {

    private final String email;
    private final String password;
    private final List<GrantedAuthority> authorities;

    @Getter
    private final Long restaurantId;

    /**
     * Initializes the parameterized constructor
     *
     * @param userInfo The user info of the user
     */
    public UserInfoDetails(User userInfo) {
        this.email = userInfo.getEmail();
        this.password = userInfo.getPassword();
        this.authorities = List.of(new SimpleGrantedAuthority(userInfo.getRole().name()));

        this.restaurantId = (userInfo.getRestaurant() == null)
                ? null
                : userInfo.getRestaurant().getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
