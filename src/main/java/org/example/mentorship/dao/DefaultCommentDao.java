package org.example.mentorship.dao;

import org.example.mentorship.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class DefaultCommentDao implements CommentDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DefaultCommentDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Comment comment) {
        jdbcTemplate.update(
                "INSERT INTO comments (content, author_id, target_id) VALUES (?, ?, ?)",
                comment.getContent(),
                comment.getAuthorId(),
                comment.getTargetId()
        );
    }

    public List<Comment> findByTargetId(int targetId) {
        return jdbcTemplate.query(
                "SELECT c.*, u.username AS author_username " +
                        "FROM comments c JOIN users u ON c.author_id = u.id_user " +
                        "WHERE c.target_id = ? ORDER BY c.posted_at DESC",
                new Object[]{targetId},
                (rs, rowNum) -> {
                    Comment comment = new BeanPropertyRowMapper<>(Comment.class).mapRow(rs, rowNum);
                    comment.setAuthorUsername(rs.getString("author_username"));
                    return comment;
                }
        );
    }

    public void deleteByTargetId(int targetId) {
        jdbcTemplate.update("DELETE FROM comments WHERE target_id = ?", targetId);
    }

}
