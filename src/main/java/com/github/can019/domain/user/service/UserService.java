package com.github.can019.domain.user.service;

import com.github.can019.domain.user.entity.User;
import com.github.can019.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public boolean existById(Long id) {
        return userRepository.existsById(id);
    }
}
