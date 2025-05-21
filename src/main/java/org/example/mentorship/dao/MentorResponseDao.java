package org.example.mentorship.dao;

import org.example.mentorship.entity.MentorResponse;
import java.util.Optional;

public interface MentorResponseDao {
    void save(MentorResponse response);
    Optional<MentorResponse> findByConsultationRequestId(int idConsultationRequest);
}
