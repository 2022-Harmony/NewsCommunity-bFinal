package com.teamharmony.newscommunity.comments.service;

import com.teamharmony.newscommunity.comments.entity.Comment;
import com.teamharmony.newscommunity.comments.entity.Likes;
import com.teamharmony.newscommunity.comments.repository.CommentRepository;
import com.teamharmony.newscommunity.comments.repository.LikesRepository;
import com.teamharmony.newscommunity.users.entity.User;
import com.teamharmony.newscommunity.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LikesService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final LikesRepository likesRepository;

    // 좋아요 테이블 업데이트
    @Transactional
    public void likes(Long commentId, String username) {

            User user = userRepository.findByUsername(username);
            Long userId = user.getId();

            // 좋아요 테이블에서 현재 로그인한 유저가 특정 댓글에 좋아요 한 이력이 있는지 조회합니다.
            Likes like = likesRepository.findByComment_CommentIdAndUser_Id(commentId, userId);

            // 위에서 조회한 결과가 존재하지 않으면 좋아요 테이블에 좋아요를 저장하고 그렇지 않으면 해당 이력을 삭제합니다.
            if (like == null) {
                Likes likes = new Likes();

                Comment comment = commentRepository.findById(commentId).orElseThrow(
                        () -> new IllegalArgumentException("댓글을 찾을 수 없습니다.")
                );

                comment.addLikes(likes);
                user.addLikes(likes);
            } else {
                likesRepository.deleteByComment_CommentIdAndUser_id(commentId, userId);
            }
    }

    // 해당 댓글이 가지고 있는 좋아요 개수를 반환합니다.
    public int LikedCommentsCount(Long id) {
        return likesRepository.findByComment_CommentId(id).size();
    }

    public boolean isLiked(Long commentId, String username) {
        if(username == null) {
            return false;
        }
        User user = userRepository.findByUsername(username);
        Long userId = user.getId();

        // 좋아요 테이블에서 현재 로그인한 유저가 특정 댓글에 좋아요 한 이력이 있는지 조회합니다.
        Likes like = likesRepository.findByComment_CommentIdAndUser_Id(commentId, userId);

        if (like == null) {
            return false;
        } else {
            return true;
        }
    }
}
