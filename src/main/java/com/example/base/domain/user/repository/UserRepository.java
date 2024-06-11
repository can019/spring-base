package com.example.base.domain.user.repository;


import com.example.base.domain.user.domain.User;
import com.example.base.global.exception.ApplicationException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository  {

    @PersistenceContext
    EntityManager em;

    public void save(User user){
        em.persist(user);
    }

    public User findOne(byte[] id){
        return em.find(User.class, id);
    }

}
