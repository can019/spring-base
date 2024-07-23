package com.github.can019.domain.user.service;

import com.github.can019.domain.user.domain.User;
import com.github.can019.domain.user.repository.UserJpaRepository;
import com.github.can019.domain.user.repository.UserRepository;
import com.github.can019.global.util.convertor.TypeConvertor;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class UserService {
    private final UserRepository userRepository;
    private final UserJpaRepository userJpaRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserJpaRepository userJpaRepository){
        this.userRepository = userRepository;
        this.userJpaRepository = userJpaRepository;
    }

    @Transactional
    public String createUser(User user){
        this.userRepository.save(user);
        String id = user.getIdAsString();
        return id;
    }

    @Transactional
    public User findOneById(String strId) {
        byte[] id = TypeConvertor.hexStringToByte(strId);
        return this.userRepository.findOneById(id);
    }
}
