package com.github.can019.domain.user.repository;

import com.github.can019.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, byte[]> {
}
