package com.picpay.dev_challengue.challengue.models;


import com.picpay.dev_challengue.challengue.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDateTime;

@Entity
@Table(name = "app_users")
@Data
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            nullable = false,
            unique = false)
    private String name;


    @Column(
            unique = true,
            nullable = false
    )
    private String email;

    @Column(
            nullable = false,
            unique = true
    )
    private String cpf;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;



}
