package org.example.mentorship.controller;

import jakarta.validation.Valid;
import org.example.mentorship.dao.ConsultationRequestDao;
import org.example.mentorship.dao.MentorResponseDao;
import org.example.mentorship.dao.UserDao;
import org.example.mentorship.entity.ConsultationRequest;
import org.example.mentorship.entity.MentorResponse;
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
@RequestMapping("/mentor")
public class MentorController {
    private final UserDao userDao;
    private final ConsultationRequestDao consultationRequestDao;
    private final MentorResponseDao mentorResponseDao;

    @Autowired
    public MentorController(UserDao userDao, ConsultationRequestDao consultationRequestDao, MentorResponseDao mentorResponseDao) {
        this.userDao = userDao;
        this.consultationRequestDao = consultationRequestDao;
        this.mentorResponseDao = mentorResponseDao;
    }

    @GetMapping("/requests")
    public String showRequests(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User mentor = userDao.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Mentor not found"));

        List<ConsultationRequest> requests = consultationRequestDao.findByMentorId(mentor.getIdUser());
        model.addAttribute("requests", requests);
        model.addAttribute("mentorResponse", new MentorResponse());
        return "mentor-requests";
    }

    @PostMapping("/requests/{id}/respond")
    public String respondToRequest(
            @PathVariable("id") int idConsultationRequest,
            @Valid @ModelAttribute("mentorResponse") MentorResponse response,
            BindingResult result,
            @RequestParam("status") String status,
            Model model
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User mentor = userDao.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Mentor not found"));

        Optional<ConsultationRequest> request = consultationRequestDao.findById(idConsultationRequest);
        if (request.isEmpty() || request.get().getIdMentor() != mentor.getIdUser()) { // Исправлено: == вместо equals
            model.addAttribute("error", "Request not found or unauthorized");
            model.addAttribute("requests", consultationRequestDao.findByMentorId(mentor.getIdUser()));
            return "mentor-requests";
        }

        if (!"confirmed".equals(status) && !"rejected".equals(status)) {
            model.addAttribute("error", "Invalid status");
            model.addAttribute("requests", consultationRequestDao.findByMentorId(mentor.getIdUser()));
            return "mentor-requests";
        }

        if (result.hasErrors()) {
            model.addAttribute("requests", consultationRequestDao.findByMentorId(mentor.getIdUser()));
            return "mentor-requests";
        }

        // Checking if the request is still in the 'pending' status
        if (!"pending".equals(request.get().getStatusRequest())) {
            model.addAttribute("error", "Request already processed");
            model.addAttribute("requests", consultationRequestDao.findByMentorId(mentor.getIdUser()));
            return "mentor-requests";
        }

        consultationRequestDao.updateStatus(idConsultationRequest, status);
        response.setIdConsultationRequest(idConsultationRequest);
        mentorResponseDao.save(response);

        return "redirect:/mentor/requests";
    }
}