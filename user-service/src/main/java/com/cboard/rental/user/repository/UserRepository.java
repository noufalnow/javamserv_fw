package com.cboard.rental.user.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import com.cboard.rental.user.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional // This annotation will handle transaction management
public class UserRepository {

    @PersistenceContext
    private EntityManager entityManager; // Injecting EntityManager

    public User save(User user) {
        if (user.getId() == null) {
            // If the user doesn't have an ID, we are creating a new user
            entityManager.persist(user);
            return user;
        } else {
            // If the user already exists, we are updating the existing user
            return entityManager.merge(user);
        }
    }

    public List<User> findAll() {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();
    }

    public Optional<User> findById(Long id) {
        User user = entityManager.find(User.class, id);
        return Optional.ofNullable(user);
    }

    public void deleteById(Long id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    public boolean existsById(Long id) {
        return findById(id).isPresent();
    }

    // New Methods Added

    public Optional<User> findByUsername(String username) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
        query.setParameter("username", username);
        return query.getResultStream().findFirst();
    }

    public List<User> findByRole(String role) {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u JOIN u.roles r WHERE r.name = :role", User.class);
        query.setParameter("role", role);
        return query.getResultList();
    }

    public void delete(User user) {
        if (entityManager.contains(user)) {
            entityManager.remove(user);
        } else {
            entityManager.remove(entityManager.merge(user));
        }
    }
}
