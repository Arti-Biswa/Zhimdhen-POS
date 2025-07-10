package com.java.Zhimdhen_POS.config;

import com.java.Zhimdhen_POS.auth.helper.UserInfoService;
import com.java.Zhimdhen_POS.filter.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter authFilter;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserInfoService(); // Ensure UserInfoService implements UserDetailsService
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/auth/login", "/api/auth/register","/api/auth/forgot-password","/api/auth/reset-password").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/users").hasAnyAuthority("SUPER_ADMIN","ADMIN","CASHIER")
                        .requestMatchers(HttpMethod.GET, "/api/users/by-restaurant").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/users/**").hasAnyAuthority("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasAnyAuthority("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/categories/**").hasAnyAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/categories/by-restaurant").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/categories/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/products/**").hasAnyAuthority("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/products/**").hasAnyAuthority("ADMIN")
                        .requestMatchers("/uploads/images/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/products/**").hasAnyAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/products/public").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/admin-products").hasAuthority("ADMIN")

                        .requestMatchers(HttpMethod.POST, "/api/tables/add").hasAuthority("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/tables/list").hasAnyAuthority("ADMIN","CASHIER")
                        .requestMatchers(HttpMethod.DELETE, "/api/tables/**").hasAuthority("ADMIN")
                        .requestMatchers("/api/qr/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/payments/submit").permitAll()
                        .requestMatchers("/api/orders/**").permitAll()

                        .requestMatchers(HttpMethod.POST, "/api/restaurants/add").hasAnyAuthority("SUPER_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/restaurants/self").hasAuthority("ADMIN")
                        .requestMatchers("/uploads/restaurants/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/restaurants/**").hasAuthority("SUPER_ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/restaurants/**").hasAnyAuthority("SUPER_ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/restaurants/**").hasAnyAuthority("SUPER_ADMIN")
                        .anyRequest().authenticated()
                )
                .sessionManagement(sessionManager -> sessionManager
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Password encoding
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
