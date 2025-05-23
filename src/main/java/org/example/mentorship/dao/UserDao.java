package org.example.mentorship.dao;

import org.example.mentorship.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    void save(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findById(int id);
    void update(User user);
    void delete(int id);
    List<User> findAll();
}
