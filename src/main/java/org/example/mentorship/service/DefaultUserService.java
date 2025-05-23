package org.example.mentorship.service;

import org.example.mentorship.dao.MentorDao;
import org.example.mentorship.dao.StudentDao;
import org.example.mentorship.dao.UserDao;
import org.example.mentorship.entity.Mentor;
import org.example.mentorship.entity.Student;
import org.example.mentorship.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserService implements UserService {

    private UserDao userDAO;
    private StudentDao studentDAO;
    private MentorDao mentorDAO;
    private PasswordEncoder passwordEncoder;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setUserDAO(UserDao userDAO) {
        this.userDAO = userDAO;
    }

    @Autowired
    public void setStudentDAO(StudentDao studentDAO) {
        this.studentDAO = studentDAO;
    }

    @Autowired
    public void setMentorDAO(MentorDao mentorDAO) {
        this.mentorDAO = mentorDAO;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void registerUser(User user, Student student, Mentor mentor) {
        // Hash password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Save user
        userDAO.save(user);

        // Retrieve the generated user ID
        Integer userId = jdbcTemplate.queryForObject("SELECT id_user FROM users WHERE email = ?", Integer.class, user.getEmail());

        if ("student".equalsIgnoreCase(user.getRole())) {
            student.setIdUser(userId);
            studentDAO.save(student);
        } else if ("mentor".equalsIgnoreCase(user.getRole())) {
            mentor.setIdUser(userId);
            mentorDAO.save(mentor);
        }
    }
}
