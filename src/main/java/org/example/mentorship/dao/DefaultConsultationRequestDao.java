package org.example.mentorship.dao;

import org.example.mentorship.entity.ConsultationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class DefaultConsultationRequestDao implements ConsultationRequestDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DefaultConsultationRequestDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(ConsultationRequest request) {
        jdbcTemplate.update(
                "INSERT INTO consultationRequests (request_message, id_student, id_mentor, status_request) VALUES (?, ?, ?, ?)",
                request.getRequestMessage(),
                request.getIdStudent(),
                request.getIdMentor(),
                request.getStatusRequest()
        );
    }

    public Optional<ConsultationRequest> findPendingByStudentAndMentor(int idStudent, int idMentor) {
        return jdbcTemplate.query(
                "SELECT * FROM consultationRequests WHERE id_student = ? AND id_mentor = ? AND status_request = 'pending'",
                new Object[]{idStudent, idMentor},
                new BeanPropertyRowMapper<>(ConsultationRequest.class)
        ).stream().findAny();
    }

    public List<ConsultationRequest> findByMentorId(int idMentor) {
        return jdbcTemplate.query(
                "SELECT cr.*, u.username AS student_username " +
                        "FROM consultationRequests cr JOIN users u ON cr.id_student = u.id_user " +
                        "WHERE cr.id_mentor = ? AND cr.status_request = 'pending' ORDER BY cr.requested_at DESC",
                new Object[]{idMentor},
                (rs, rowNum) -> {
                    ConsultationRequest request = new BeanPropertyRowMapper<>(ConsultationRequest.class).mapRow(rs, rowNum);
                    request.setStudentUsername(rs.getString("student_username"));
                    return request;
                }
        );
    }

    public List<ConsultationRequest> findByStudentId(int idStudent) {
        return jdbcTemplate.query(
                "SELECT cr.*, u.username AS mentor_username, mr.response_message, mr.sent_at " +
                        "FROM consultationRequests cr " +
                        "JOIN users u ON cr.id_mentor = u.id_user " +
                        "LEFT JOIN mentorResponses mr ON cr.id_consultation_request = mr.id_consultation_request " +
                        "WHERE cr.id_student = ? ORDER BY cr.requested_at DESC",
                new Object[]{idStudent},
                (rs, rowNum) -> {
                    ConsultationRequest request = new BeanPropertyRowMapper<>(ConsultationRequest.class).mapRow(rs, rowNum);
                    request.setMentorUsername(rs.getString("mentor_username"));
                    request.setResponseMessage(rs.getString("response_message"));
                    request.setSentAt(rs.getTimestamp("sent_at") != null ? rs.getTimestamp("sent_at").toLocalDateTime() : null);
                    return request;
                }
        );
    }

    public void updateStatus(int idConsultationRequest, String status) {
        jdbcTemplate.update(
                "UPDATE consultationRequests SET status_request = ? WHERE id_consultation_request = ?",
                status,
                idConsultationRequest
        );
    }

    public Optional<ConsultationRequest> findById(int idConsultationRequest) {
        return jdbcTemplate.query(
                "SELECT * FROM consultationRequests WHERE id_consultation_request = ?",
                new Object[]{idConsultationRequest},
                new BeanPropertyRowMapper<>(ConsultationRequest.class)
        ).stream().findAny();
    }
}
