package org.example.mentorship.dao;

import org.example.mentorship.entity.MentorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class DefaultMentorResponseDao implements MentorResponseDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DefaultMentorResponseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(MentorResponse response) {
        jdbcTemplate.update(
                "INSERT INTO mentorResponses (id_consultation_request, response_message) VALUES (?, ?)",
                response.getIdConsultationRequest(),
                response.getResponseMessage()
        );
    }

    public Optional<MentorResponse> findByConsultationRequestId(int idConsultationRequest) {
        return jdbcTemplate.query(
                "SELECT * FROM mentorResponses WHERE id_consultation_request = ?",
                new Object[]{idConsultationRequest},
                new BeanPropertyRowMapper<>(MentorResponse.class)
        ).stream().findAny();
    }
}
