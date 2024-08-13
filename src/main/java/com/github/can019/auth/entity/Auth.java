package com.github.can019.auth.entity;

import com.github.can019.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "auth")
@Data
public class Auth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String password;

    @Column
    private String role;

    @Column
    private String refreshToken;

    @OneToOne
    private User user;
}