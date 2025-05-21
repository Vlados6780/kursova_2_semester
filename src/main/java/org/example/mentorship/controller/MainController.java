package org.example.mentorship.controller;

import org.example.mentorship.dao.MentorDao;
import org.example.mentorship.dao.StudentDao;
import org.example.mentorship.dao.UserDao;
import org.example.mentorship.entity.Mentor;
import org.example.mentorship.entity.Student;
import org.example.mentorship.entity.User;
import org.example.mentorship.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    private UserService userService;
    private UserDao userDao;
    private StudentDao studentDao;
    private MentorDao mentorDao;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setStudentDao(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Autowired
    public void setMentorDao(MentorDao mentorDao) {
        this.mentorDao = mentorDao;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("student", new Student());
        model.addAttribute("mentor", new Mentor());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @Valid @ModelAttribute("user") User user,
            BindingResult userResult,
            @ModelAttribute("student") Student student,
            BindingResult studentResult,
            @ModelAttribute("mentor") Mentor mentor,
            BindingResult mentorResult
    ) {
        if (userResult.hasErrors() ||
                ("student".equalsIgnoreCase(user.getRole()) && studentResult.hasErrors()) ||
                ("mentor".equalsIgnoreCase(user.getRole()) && mentorResult.hasErrors())) {
            return "register";
        }
        userService.registerUser(user, student, mentor);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/student/home")
    public String studentHome(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userDao.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        Student student = studentDao.findByUserId(user.getIdUser()).orElse(new Student());
        model.addAttribute("user", user);
        model.addAttribute("student", student);
        return "student-home";
    }

    @GetMapping("/mentor/home")
    public String mentorHome(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userDao.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        Mentor mentor = mentorDao.findByUserId(user.getIdUser()).orElse(new Mentor());
        model.addAttribute("user", user);
        model.addAttribute("mentor", mentor);
        return "mentor-home";
    }

    @PostMapping("/update")
    public String updateUser(
            @Valid @ModelAttribute("user") User user,
            BindingResult userResult,
            @ModelAttribute("student") Student student,
            BindingResult studentResult,
            @ModelAttribute("mentor") Mentor mentor,
            BindingResult mentorResult,
            Model model
    ) {
        if (userResult.hasErrors() ||
                (student != null && studentResult.hasErrors()) ||
                (mentor != null && mentorResult.hasErrors())) {
            model.addAttribute("error", "Validation failed");
            String role = user.getRole() != null ? user.getRole() : "student"; // Fallback role
            return role.equalsIgnoreCase("student") ? "student-home" : "mentor-home";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User currentUser = userDao.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        // Updating user data while keeping immutable fields
        user.setIdUser(currentUser.getIdUser());
        user.setPassword(currentUser.getPassword());
        user.setEmail(currentUser.getEmail());
        user.setRole(currentUser.getRole());
        userDao.update(user);

        // Updating student or mentor data
        if ("student".equalsIgnoreCase(user.getRole())) {
            student.setIdUser(user.getIdUser());
            studentDao.update(student);
        } else if ("mentor".equalsIgnoreCase(user.getRole())) {
            mentor.setIdUser(user.getIdUser());
            mentorDao.update(mentor);
        }

        return "redirect:/" + user.getRole().toLowerCase() + "/home";
    }

    @PostMapping("/delete")
    public String deleteAccount() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = userDao.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        if ("student".equalsIgnoreCase(user.getRole())) {
            studentDao.delete(user.getIdUser());
        } else if ("mentor".equalsIgnoreCase(user.getRole())) {
            mentorDao.delete(user.getIdUser());
        }
        userDao.delete(user.getIdUser());
        SecurityContextHolder.clearContext();
        return "redirect:/login";
    }

}