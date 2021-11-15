package com.example.crudboot.dao;

import com.example.crudboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserDaoImpl
        implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserDaoImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        entityManager.persist(user);
    }

    @Override
    public List<User> listUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @Override
    public void deleteUser(int id) {
        entityManager.remove(getUser(id));
    }

    @Override
    public User getUser(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void updateUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        entityManager.merge(user);
    }

    @Override
    public User getUser(String emailin) {
        Query query = entityManager.createQuery("select u FROM User u where u.email = :email", User.class);
        return (User) query.setParameter("email", emailin).getSingleResult();

    }
}
