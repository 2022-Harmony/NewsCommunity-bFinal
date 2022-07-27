package com.teamharmony.newscommunity.comments.controller;

import com.teamharmony.newscommunity.comments.service.LikesService;
import com.teamharmony.newscommunity.common.annotation.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class LikesController {

    private final LikesService likesService;

    /**
     * 좋아요 요청을 받는 API
     * @param id
     * @param username
     */
    @PostMapping("/user/likes/{id}")
    public ResponseEntity<?> likes(@PathVariable Long id,
                                   @CurrentUser String username) {
        likesService.likes(id, username);
        return ResponseEntity.ok().body("좋아요");
    }

    /**
     * 특정 댓글에 표시된 좋아요 개수를 보여주는 API
     * @param commentId
     * @return
     */
    @GetMapping("/user/likes/count/{commentId}")
    public ResponseEntity<Integer> getLikedCommentsCountByCommentId(@PathVariable Long commentId) {
        return ResponseEntity.ok().body(likesService.LikedCommentsCount(commentId));
    }

    @GetMapping("/user/likes/isLiked/{commentId}")
    public ResponseEntity<Boolean> isLiked(@PathVariable Long commentId, @CurrentUser String username) {
        return ResponseEntity.ok().body(likesService.isLiked(commentId, username));
    }
}
