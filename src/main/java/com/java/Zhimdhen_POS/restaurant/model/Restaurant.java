package com.java.Zhimdhen_POS.restaurant.model;

import com.java.Zhimdhen_POS.users.model.User;
import com.java.Zhimdhen_POS.utils.AuditEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;

@EqualsAndHashCode(callSuper = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@DynamicInsert
@DynamicUpdate
@Table(name = "restaurants")
public class Restaurant extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String restaurant_name;

    @Column(unique = true, nullable = false)
    private String license_no;

    @NotNull
    private String address;

    @Column
    private String image;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
