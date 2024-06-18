package com.example.base.domain.user.repository;


import com.example.base.domain.user.domain.User;
import com.example.base.global.exception.ApplicationException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    public User findOneById(byte[] id){
        return em.find(User.class, id);
    }

}
