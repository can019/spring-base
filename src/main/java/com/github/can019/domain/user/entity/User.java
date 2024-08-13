package com.github.can019.domain.user.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users",indexes = {
        @Index(name = "idx_user_email", columnList = "email", unique = true)})
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;
}
