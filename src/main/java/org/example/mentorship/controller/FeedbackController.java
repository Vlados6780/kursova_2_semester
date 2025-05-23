package org.example.mentorship.controller;

import jakarta.validation.Valid;
import org.example.mentorship.dao.CommentDao;
import org.example.mentorship.dao.UserDao;
import org.example.mentorship.entity.Comment;
import org.example.mentorship.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {
    private final UserDao userDao;
    private final CommentDao commentDao;

    @Autowired
    public FeedbackController(UserDao userDao, CommentDao commentDao) {
        this.userDao = userDao;
        this.commentDao = commentDao;
    }

    @GetMapping
    public String showFeedbackPage(Model model) {
        List<User> users = userDao.findAll();
        model.addAttribute("users", users);
        return "feedback";
    }

    @GetMapping("/{id}")
    public String showUserComments(@PathVariable("id") int targetId, Model model) {
        Optional<User> targetUser = userDao.findById(targetId);
        if (targetUser.isEmpty()) {
            model.addAttribute("error", "User not found");
            return "feedback";
        }

        List<Comment> comments = commentDao.findByTargetId(targetId);
        model.addAttribute("targetUser", targetUser.get());
        model.addAttribute("comments", comments);
        model.addAttribute("newComment", new Comment());
        return "user-comments";
    }

    @PostMapping("/{id}/comment")
    public String addComment(
            @PathVariable("id") int targetId,
            @Valid @ModelAttribute("newComment") Comment comment,
            BindingResult result,
            Model model
    ) {
        Optional<User> targetUser = userDao.findById(targetId);
        if (targetUser.isEmpty()) {
            model.addAttribute("error", "User not found");
            return "feedback";
        }

        if (result.hasErrors()) {
            model.addAttribute("targetUser", targetUser.get());
            model.addAttribute("comments", commentDao.findByTargetId(targetId));
            return "user-comments";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User author = userDao.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        comment.setAuthorId(author.getIdUser());
        comment.setTargetId(targetId);
        commentDao.save(comment);

        return "redirect:/feedback/" + targetId;
    }

}
