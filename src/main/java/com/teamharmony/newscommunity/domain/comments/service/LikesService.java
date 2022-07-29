package com.teamharmony.newscommunity.domain.comments.service;

import com.teamharmony.newscommunity.domain.comments.entity.Comment;
import com.teamharmony.newscommunity.domain.comments.entity.Likes;
import com.teamharmony.newscommunity.domain.comments.repository.CommentRepository;
import com.teamharmony.newscommunity.domain.comments.repository.LikesRepository;
import com.teamharmony.newscommunity.domain.users.entity.User;
import com.teamharmony.newscommunity.domain.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class LikesService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final LikesRepository likesRepository;

    /**
     * 특정 유저가 좋아요를 했는지를 확인하기 위해 DB에서 찾아서 결과가 있다면 좋아요 요청 시 DB에서 데이터 삭제, 반대의 경우에 DB에 데이터 추가
     * @param commentId
     * @param username
     */
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

    /**
     * 특정 댓글의 좋아요 개수 반환
     * @param id
     * @return 댓글 좋아요 개수
     */
    public int LikedCommentsCount(Long id) {
        return likesRepository.findByComment_CommentId(id).size();
    }

    /**
     * 유저가 특정 댓글에 좋아요를 한 이력이 있는지를 확인
     * @param commentId
     * @param username
     * @return 좋아요를 한 이력이 있다면 true, 반대의 경우에 false 반환
     */
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
