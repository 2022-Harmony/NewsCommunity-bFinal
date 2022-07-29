package com.teamharmony.newscommunity.domain.comments.repository;

import com.teamharmony.newscommunity.domain.comments.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    List<Likes> findByComment_CommentId(Long commentId);
    Likes findByComment_CommentIdAndUser_Id(Long commentId, Long userId);
    void deleteByComment_CommentIdAndUser_id(Long commentId, Long userId);
}
