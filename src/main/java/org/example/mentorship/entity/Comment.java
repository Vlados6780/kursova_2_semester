package org.example.mentorship.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public class Comment {
    private int idComment;
    private String content;
    private LocalDateTime postedAt;
    private int authorId;
    private int targetId;

    private String authorUsername; // for display on html

    public Comment() {
    }

    public Comment(int idComment, String content, LocalDateTime postedAt, int authorId, int targetId, String authorUsername) {
        this.idComment = idComment;
        this.content = content;
        this.postedAt = postedAt;
        this.authorId = authorId;
        this.targetId = targetId;
        this.authorUsername = authorUsername;
    }

    public int getIdComment() {
        return idComment;
    }

    @NotBlank(message = "Comment cannot be empty")
    @Size(max = 500, message = "Comment must be less than 500 characters")
    public String getContent() {
        return content;
    }

    public LocalDateTime getPostedAt() {
        return postedAt;
    }

    public int getAuthorId() {
        return authorId;
    }

    public int getTargetId() {
        return targetId;
    }

    public void setIdComment(int idComment) {
        this.idComment = idComment;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPostedAt(LocalDateTime postedAt) {
        this.postedAt = postedAt;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }
}
