package com.teamharmony.newscommunity.domain.comments.repository;

import com.teamharmony.newscommunity.domain.comments.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByNewsId(String newsId, Pageable pageable);
    Page<Comment> findByUser_Id(Long userId, Pageable pageable);
    Integer countByNewsId(String newsId);
}
