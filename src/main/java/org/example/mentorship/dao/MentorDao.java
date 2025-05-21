package org.example.mentorship.dao;

import org.example.mentorship.entity.Mentor;
import java.util.List;
import java.util.Optional;

public interface MentorDao {
    void save(Mentor mentor);
    Optional<Mentor> findByUserId(int idUser);
    void update(Mentor mentor);
    void delete(int idUser);
    List<Mentor> findAll();
}
