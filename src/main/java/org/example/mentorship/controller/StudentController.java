package org.example.mentorship.controller;

import jakarta.validation.Valid;
import org.example.mentorship.dao.ConsultationRequestDao;
import org.example.mentorship.dao.MentorDao;
import org.example.mentorship.dao.UserDao;
import org.example.mentorship.entity.ConsultationRequest;
import org.example.mentorship.entity.Mentor;
import org.example.mentorship.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/student")
public class StudentController {
    private final UserDao userDao;
    private final MentorDao mentorDao;
    private final ConsultationRequestDao consultationRequestDao;

    @Autowired
    public StudentController(UserDao userDao, MentorDao mentorDao, ConsultationRequestDao consultationRequestDao) {
        this.userDao = userDao;
        this.mentorDao = mentorDao;
        this.consultationRequestDao = consultationRequestDao;
    }

    @GetMapping("/mentors")
    public String showMentors(Model model) {
        List<Mentor> mentors = mentorDao.findAll();
        model.addAttribute("mentors", mentors);
        model.addAttribute("consultationRequest", new ConsultationRequest());
        return "student-mentors";
    }

    @PostMapping("/mentors/{id}/request")
    public String sendRequest(
            @PathVariable("id") int idMentor,
            @Valid @ModelAttribute("consultationRequest") ConsultationRequest request,
            BindingResult result,
            Model model
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User student = userDao.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Optional<Mentor> mentor = mentorDao.findByUserId(idMentor);
        if (mentor.isEmpty()) {
            model.addAttribute("error", "Mentor not found");
            model.addAttribute("mentors", mentorDao.findAll());
            return "student-mentors";
        }

        // Check for an existing pending request
        Optional<ConsultationRequest> existingRequest = consultationRequestDao.findPendingByStudentAndMentor(student.getIdUser(), idMentor);
        if (existingRequest.isPresent()) {
            model.addAttribute("error", "You already have a pending request to this mentor");
            model.addAttribute("mentors", mentorDao.findAll());
            return "student-mentors";
        }

        if (result.hasErrors()) {
            model.addAttribute("mentors", mentorDao.findAll());
            return "student-mentors";
        }

        request.setIdStudent(student.getIdUser());
        request.setIdMentor(idMentor);
        request.setStatusRequest("pending");
        consultationRequestDao.save(request);

        return "redirect:/student/mentors";
    }

    @GetMapping("/requests")
    public String showRequests(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User student = userDao.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        List<ConsultationRequest> requests = consultationRequestDao.findByStudentId(student.getIdUser());
        model.addAttribute("requests", requests);
        return "student-requests";
    }
}
