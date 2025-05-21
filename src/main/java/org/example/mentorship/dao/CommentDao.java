package org.example.mentorship.dao;

import org.example.mentorship.entity.Comment;
import java.util.List;

public interface CommentDao {
    void save(Comment comment);
    List<Comment> findByTargetId(int targetId);
    void deleteByTargetId(int targetId);
}
