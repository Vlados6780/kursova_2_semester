package org.example.mentorship.entity;


public class Mentor {
    private int idMentor;
    private int idUser;
    private String specialization;
    private int experienceYears;

    //for display on html
    private String username;
    private int age;

    public Mentor() {
    }

    public Mentor(int idMentor, int idUser, String specialization, int experienceYears,
                  String username, int age) {
        this.idMentor = idMentor;
        this.idUser = idUser;
        this.specialization = specialization;
        this.experienceYears = experienceYears;

        this.username = username;
        this.age = age;
    }

    public int getIdMentor() {
        return idMentor;
    }

    public int getIdUser() {
        return idUser;
    }

    public String getSpecialization() {
        return specialization;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    // Сетери
    public void setIdMentor(int idMentor) {
        this.idMentor = idMentor;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
