package org.example.mentorship.dao;

import org.example.mentorship.entity.Student;
import java.util.Optional;

public interface StudentDao {
    void save(Student student);
    Optional<Student> findByUserId(int idUser);
    void update(Student student);
    void delete(int idUser);
}
