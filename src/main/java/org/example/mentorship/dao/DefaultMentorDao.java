package org.example.mentorship.dao;

import org.example.mentorship.entity.Mentor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class DefaultMentorDao implements MentorDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DefaultMentorDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Mentor mentor) {
        jdbcTemplate.update(
                "INSERT INTO mentors (id_user, specialization, experience_years) VALUES (?, ?, ?)",
                mentor.getIdUser(),
                mentor.getSpecialization(),
                mentor.getExperienceYears()
        );
    }
    public Optional<Mentor> findByUserId(int idUser) {
        return jdbcTemplate.query(
                "SELECT * FROM mentors WHERE id_user = ?",
                new Object[]{idUser},
                new BeanPropertyRowMapper<>(Mentor.class)
        ).stream().findAny();
    }

    public void update(Mentor mentor) {
        jdbcTemplate.update(
                "UPDATE mentors SET specialization = ?, experience_years = ? WHERE id_user = ?",
                mentor.getSpecialization(),
                mentor.getExperienceYears(),
                mentor.getIdUser()
        );
    }

    public void delete(int idUser) {
        jdbcTemplate.update(
                "DELETE FROM mentors WHERE id_user = ?",
                idUser
        );
    }
    public List<Mentor> findAll() {
        return jdbcTemplate.query(
                "SELECT m.*, u.username, u.age FROM mentors m JOIN users u ON m.id_user = u.id_user",
                (rs, rowNum) -> {
                    Mentor mentor = new BeanPropertyRowMapper<>(Mentor.class).mapRow(rs, rowNum);
                    mentor.setUsername(rs.getString("username"));
                    mentor.setAge(rs.getInt("age"));
                    return mentor;
                }
        );
    }
}
