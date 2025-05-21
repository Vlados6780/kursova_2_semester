package org.example.mentorship.entity;

import java.time.LocalDateTime;

public class ConsultationRequest {
    private int idConsultationRequest;
    private String requestMessage;
    private LocalDateTime requestedAt;
    private String statusRequest; // "pending", "confirmed", "rejected"
    private int idStudent;
    private int idMentor;


    //for display on html
    private String studentUsername;
    private String mentorUsername;
    private String responseMessage;
    private LocalDateTime sentAt;

    public ConsultationRequest() {
    }

    public ConsultationRequest(int idConsultationRequest, String requestMessage, LocalDateTime requestedAt, String statusRequest, int idStudent, int idMentor,
                               String studentUsername,
                               String mentorUsername, String responseMessage, LocalDateTime sentAt) {
        this.idConsultationRequest = idConsultationRequest;
        this.requestMessage = requestMessage;
        this.requestedAt = requestedAt;
        this.statusRequest = statusRequest;
        this.idStudent = idStudent;
        this.idMentor = idMentor;

        this.studentUsername = studentUsername;
        this.mentorUsername = mentorUsername;
        this.responseMessage = responseMessage;
        this.sentAt = sentAt;
    }

    public int getIdConsultationRequest() {
        return idConsultationRequest;
    }

    public String getRequestMessage() {
        return requestMessage;
    }

    public LocalDateTime getRequestedAt() {
        return requestedAt;
    }

    public String getStatusRequest() {
        return statusRequest;
    }

    public int getIdStudent() {
        return idStudent;
    }

    public int getIdMentor() {
        return idMentor;
    }

    public void setIdConsultationRequest(int idConsultationRequest) {
        this.idConsultationRequest = idConsultationRequest;
    }

    public void setRequestMessage(String requestMessage) {
        this.requestMessage = requestMessage;
    }

    public void setRequestedAt(LocalDateTime requestedAt) {
        this.requestedAt = requestedAt;
    }

    public void setStatusRequest(String statusRequest) {
        this.statusRequest = statusRequest;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    public void setIdMentor(int idMentor) {
        this.idMentor = idMentor;
    }

    public String getStudentUsername() {
        return studentUsername;
    }

    public void setStudentUsername(String studentUsername) {
        this.studentUsername = studentUsername;
    }

    public String getMentorUsername() {
        return mentorUsername;
    }

    public void setMentorUsername(String mentorUsername) {
        this.mentorUsername = mentorUsername;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }
}
