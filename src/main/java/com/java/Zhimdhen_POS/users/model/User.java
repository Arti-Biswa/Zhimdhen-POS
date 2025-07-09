package com.java.Zhimdhen_POS.users.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.java.Zhimdhen_POS.restaurant.model.Restaurant;
import com.java.Zhimdhen_POS.utils.AuditEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@DynamicInsert
@DynamicUpdate
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    @Size(min = 8)
    private String password;

    @Column(name = "phone_number")
    @Size(min = 8, max=8)
    private Long phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column
    private Role role;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public enum Role{
        SUPER_ADMIN,
        ADMIN,
        CASHIER
    }

}
