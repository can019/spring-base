package com.example.base.domain.user.service;

import com.example.base.domain.user.domain.User;
import com.example.base.domain.user.repository.UserJpaRepository;
import com.example.base.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.base.global.util.convertor.TypeConvertor.hexStringToByte;

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
        byte[] id = hexStringToByte(strId);
        return this.userRepository.findOneById(id);
    }
}
