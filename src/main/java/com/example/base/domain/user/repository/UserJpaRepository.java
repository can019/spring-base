package com.example.base.domain.user.repository;

import com.example.base.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, byte[]> {
}
