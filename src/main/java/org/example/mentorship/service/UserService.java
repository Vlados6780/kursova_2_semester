package org.example.mentorship.service;

import org.example.mentorship.entity.Mentor;
import org.example.mentorship.entity.Student;
import org.example.mentorship.entity.User;

public interface UserService {
    void registerUser(User user, Student student, Mentor mentor);
}
