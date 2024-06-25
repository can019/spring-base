package com.example.base.domain.user.repository;


import com.example.base.domain.user.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.example.base.global.util.convertor.TypeConvertor.hexStringToByte;

@Repository
public class UserRepository  {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public User save(User user){
        em.persist(user);
        return user;
    }

    @Transactional
    public User findOneById(String strId){
        byte[] id = this.stringIdToByte(strId);
        return em.find(User.class, id);
    }

    public byte[] stringIdToByte(String strId) {
        byte[] id = hexStringToByte(strId);
        return id;
    }
}
