package com.example.base.domain.user.repository;


import com.example.base.domain.user.domain.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

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
