package com.example.base.domain.user.service;

import com.example.base.domain.user.domain.User;
import com.example.base.domain.user.repository.UserRepository;
import com.example.base.global.util.convertor.TypeConvertor;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public String createUser(User user){
        this.userRepository.save(user);
        String id = TypeConvertor.byteArrayToHexString(user.getId());

        this.logger.warn(id);

        return id;
    }

}
