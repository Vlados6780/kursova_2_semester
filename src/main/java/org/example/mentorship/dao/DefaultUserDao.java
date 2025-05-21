package org.example.mentorship.dao;

import org.example.mentorship.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class DefaultUserDao implements UserDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DefaultUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(User user) {
        jdbcTemplate.update(
                "INSERT INTO users (username, age, email, password, role, profile_description) VALUES (?, ?, ?, ?, ?, ?)",
                user.getUsername(),
                user.getAge(),
                user.getEmail(),
                user.getPassword(),
                user.getRole(),
                user.getProfileDescription()
        );
    }

    public Optional<User> findByEmail(String email) {
        return jdbcTemplate.query(
                "SELECT * FROM users WHERE email = ?",
                new Object[]{email},
                new BeanPropertyRowMapper<>(User.class)
        ).stream().findAny();
    }

    public Optional<User> findById(int id) {
        return jdbcTemplate.query(
                "SELECT * FROM users WHERE id_user = ?",
                new Object[]{id},
                new BeanPropertyRowMapper<>(User.class)
        ).stream().findAny();
    }

    public void update(User user) {
        jdbcTemplate.update(
                "UPDATE users SET username = ?, age = ?, password = ?, role = ?, profile_description = ? WHERE id_user = ?",
                user.getUsername(),
                user.getAge(),
                user.getPassword(),
                user.getRole(),
                user.getProfileDescription(),
                user.getIdUser()
        );
    }

    public void delete(int id) {
        jdbcTemplate.update(
                "DELETE FROM users WHERE id_user = ?",
                id
        );
    }
    public List<User> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM users",
                new BeanPropertyRowMapper<>(User.class)
        );
    }
}
