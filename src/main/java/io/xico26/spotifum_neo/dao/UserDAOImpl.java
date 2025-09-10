package io.xico26.spotifum_neo.dao;

import io.xico26.spotifum_neo.entity.User;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {
    private final EntityManager em;

    @Autowired
    public UserDAOImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public User findById(int userId) {
        return em.find(User.class, userId);
    }

    @Override
    public List<User> findAll() {
        return em.createQuery("FROM User", User.class).getResultList();
    }

    @Override
    public User findByUsername(String username) {
        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username=:username", User.class);
            query.setParameter("username", username);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public User findByEmail(String email) {
        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.email=:email", User.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void save(User u) {
        em.persist(u);
    }

    @Override
    public void deleteById(int id) {
        User u = em.find(User.class, id);
        if  (u != null) {
            em.remove(u);
        }
    }

    @Override
    public void update(User u) {
        em.merge(u);
    }

}
