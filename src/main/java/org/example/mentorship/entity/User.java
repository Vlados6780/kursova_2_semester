package org.example.mentorship.entity;

import java.time.LocalDateTime;

public class User {
    private int idUser;
    private String username;
    private int age;
    private String email;
    private String password;
    private String role; // "student" or "mentor"
    private String profileDescription;
    private LocalDateTime dateRegistration;

    public User() {
    }

    public User(int idUser, String username, int age, String email, String password, String role, String profileDescription, LocalDateTime dateRegistration) {
        this.idUser = idUser;
        this.username = username;
        this.age = age;
        this.email = email;
        this.password = password;
        this.role = role;
        this.profileDescription = profileDescription;
        this.dateRegistration = dateRegistration;
    }

    public int getIdUser() {
        return idUser;
    }

    public String getUsername() {
        return username;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getProfileDescription() {
        return profileDescription;
    }

    public LocalDateTime getDateRegistration() {
        return dateRegistration;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setProfileDescription(String profileDescription) {
        this.profileDescription = profileDescription;
    }

    public void setDateRegistration(LocalDateTime dateRegistration) {
        this.dateRegistration = dateRegistration;
    }
}
