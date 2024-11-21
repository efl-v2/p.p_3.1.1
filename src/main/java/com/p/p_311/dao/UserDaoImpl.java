package com.p.p_311.dao;


import org.springframework.stereotype.Repository;
import com.p.p_311.model.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addUser(User user) {
        em.persist(user);
    }

    @Override
    public List<User> getAllUsers() {
        return em.createQuery("FROM User", User.class).getResultList();
    }

    @Override
    public void removeUserById(Integer userId) throws EntityNotFoundException {
        User user = getUserById(userId);
        if (user != null) {
            em.remove(user);
        } else {
            throw new EntityNotFoundException("User: " + userId + " not found.");
        }
    }

    @Override
    public User getUserById(Integer userId) throws EntityNotFoundException {
        User user = em.find(User.class, userId);
        if (user == null) {
            throw new EntityNotFoundException("User: " + userId + " not found.");
        }
        return user;
    }

    @Override
    public void updateUser(User user) throws EntityNotFoundException {
        User existingUser = getUserById(user.getId());
        if (existingUser != null) {
            em.merge(user);
        } else {
            throw new EntityNotFoundException("User: " + user.getId() + " not found.");
        }
    }
}
