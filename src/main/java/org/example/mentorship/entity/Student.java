package org.example.mentorship.entity;


public class Student {
    private int idStudent;
    private int idUser;
    private String university;
    private int studyYear;
    private String educationLevel;

    public Student() {
    }

    public Student(int idStudent, int idUser, String university, int studyYear, String educationLevel) {
        this.idStudent = idStudent;
        this.idUser = idUser;
        this.university = university;
        this.studyYear = studyYear;
        this.educationLevel = educationLevel;
    }

    public int getIdStudent() {
        return idStudent;
    }

    public int getIdUser() {
        return idUser;
    }

    public String getUniversity() {
        return university;
    }

    public int getStudyYear() {
        return studyYear;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public void setStudyYear(int studyYear) {
        this.studyYear = studyYear;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }
}
