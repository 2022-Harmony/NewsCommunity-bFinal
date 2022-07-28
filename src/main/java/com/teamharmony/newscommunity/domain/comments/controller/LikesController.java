package com.teamharmony.newscommunity.domain.comments.controller;

import com.teamharmony.newscommunity.domain.comments.service.LikesService;
import com.teamharmony.newscommunity.common.annotation.CurrentUser;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "댓글의 아이디", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "username", value = "현재 로그인 한 유저", required = true, paramType = "body")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "좋아요 성공")
    })
    @ApiOperation(value = "좋아요 작동")
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commentId", value = "댓글의 아이디", required = true, dataType = "int", paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "좋아요 개수 조회 성공")
    })
    @ApiOperation(value = "좋아요 개수 조회")
    @GetMapping("/user/likes/count/{commentId}")
    public ResponseEntity<Integer> getLikedCommentsCountByCommentId(@PathVariable Long commentId) {
        return ResponseEntity.ok().body(likesService.LikedCommentsCount(commentId));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "commentId", value = "댓글의 아이디", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "username", value = "현재 로그인 한 유저", required = true, paramType = "body")
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "좋아요 여부 확인 성공")
    })
    @ApiOperation(value = "좋아요 여부 확인")
    @GetMapping("/user/likes/isLiked/{commentId}")
    public ResponseEntity<Boolean> isLiked(@PathVariable Long commentId, @CurrentUser String username) {
        return ResponseEntity.ok().body(likesService.isLiked(commentId, username));
    }
}
