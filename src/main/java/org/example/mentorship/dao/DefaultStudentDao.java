package org.example.mentorship.dao;

import org.example.mentorship.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class DefaultStudentDao implements StudentDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DefaultStudentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Student student) {
        jdbcTemplate.update(
                "INSERT INTO students (id_user, university, study_year, education_level) VALUES (?, ?, ?, ?)",
                student.getIdUser(),
                student.getUniversity(),
                student.getStudyYear(),
                student.getEducationLevel()
        );
    }
    public Optional<Student> findByUserId(int idUser) {
        return jdbcTemplate.query(
                "SELECT * FROM students WHERE id_user = ?",
                new Object[]{idUser},
                new BeanPropertyRowMapper<>(Student.class)
        ).stream().findAny();
    }

    public void update(Student student) {
        jdbcTemplate.update(
                "UPDATE students SET university = ?, study_year = ?, education_level = ? WHERE id_user = ?",
                student.getUniversity(),
                student.getStudyYear(),
                student.getEducationLevel(),
                student.getIdUser()
        );
    }

    public void delete(int idUser) {
        jdbcTemplate.update(
                "DELETE FROM students WHERE id_user = ?",
                idUser
        );
    }
}
