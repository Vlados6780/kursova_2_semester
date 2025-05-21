package org.example.mentorship.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public class MentorResponse {
    private int idMentorResponse;
    private int idConsultationRequest;
    private String responseMessage;
    private LocalDateTime sentAt;

    public MentorResponse() {
    }

    public MentorResponse(int idMentorResponse, int idConsultationRequest, String responseMessage, LocalDateTime sentAt) {
        this.idMentorResponse = idMentorResponse;
        this.idConsultationRequest = idConsultationRequest;
        this.responseMessage = responseMessage;
        this.sentAt = sentAt;
    }

    public int getIdMentorResponse() {
        return idMentorResponse;
    }

    public int getIdConsultationRequest() {
        return idConsultationRequest;
    }

    @NotBlank(message = "Response message cannot be empty")
    @Size(max = 500, message = "Response message must be less than 500 characters")
    public String getResponseMessage() {
        return responseMessage;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setIdMentorResponse(int idMentorResponse) {
        this.idMentorResponse = idMentorResponse;
    }

    public void setIdConsultationRequest(int idConsultationRequest) {
        this.idConsultationRequest = idConsultationRequest;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }
}
