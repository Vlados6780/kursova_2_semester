package org.example.mentorship.dao;

import org.example.mentorship.entity.ConsultationRequest;
import java.util.List;
import java.util.Optional;

public interface ConsultationRequestDao {
    void save(ConsultationRequest request);
    Optional<ConsultationRequest> findPendingByStudentAndMentor(int idStudent, int idMentor);
    List<ConsultationRequest> findByMentorId(int idMentor);
    List<ConsultationRequest> findByStudentId(int idStudent);
    void updateStatus(int idConsultationRequest, String status);
    Optional<ConsultationRequest> findById(int idConsultationRequest);
}
