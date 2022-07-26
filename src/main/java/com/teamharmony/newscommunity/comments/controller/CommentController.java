package com.teamharmony.newscommunity.comments.controller;

import com.teamharmony.newscommunity.comments.dto.CommentCreateRequestDto;
import com.teamharmony.newscommunity.comments.dto.CommentEditRequestDto;
import com.teamharmony.newscommunity.comments.dto.CommentResponseDto;
import com.teamharmony.newscommunity.comments.service.CommentService;
import com.teamharmony.newscommunity.common.annotation.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    /**
     * requestBody로 넘어오는 댓글내용과 newsId를 받아서 저장합니다.
     * @param commentCreateRequestDto
     * @param username
     * @return 저장된 댓글의 id
     */
    @PostMapping("/user/comments")
    public ResponseEntity<?> saveComment(@RequestBody @Valid CommentCreateRequestDto commentCreateRequestDto,
                                         @CurrentUser String username) {
        commentService.createComment(commentCreateRequestDto, username);
        return ResponseEntity.ok().body("댓글 작성 성공");
    }

    /**
     * 해당 뉴스에 해당하는 댓글 목록을 보여줍니다.
     * @param news_id
     * @return 해당 뉴스 아이디에 해당하는 댓글 리스트
     */
    @GetMapping("/comments/{news_id}/{currentUser}")
    public ResponseEntity<Page<CommentResponseDto>> getComment(@PathVariable String news_id,
                                                @RequestParam("page") int page,
                                                @RequestParam("size") int size,
                                                @RequestParam("sortBy") String sortBy,
                                                @RequestParam("isAsc") boolean isAsc,
                                                @PathVariable String currentUser) {
        return ResponseEntity.ok().body(commentService.findComments(news_id, (page-1), size, sortBy, isAsc, currentUser));
    }


    /**
     * 특정 댓글을 수정합니다.
     * @param id
     * @param commentEditRequestDto
     * @return 수정된 댓글의 id
     */
    @PutMapping("/user/comments/{id}")
    public ResponseEntity<?> editComment(@PathVariable Long id,
                                         @RequestBody @Valid CommentEditRequestDto commentEditRequestDto) {
        commentService.updateComment(id, commentEditRequestDto);
        return ResponseEntity.ok().body("수정 성공");
    }

    /**
     * 특정 댓글을 삭제합니다.
     * @param id
     * @return 삭제된 댓글의 id
     */
    @DeleteMapping("/user/comments/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok().body("삭제 성공");
    }

    /**
     * 해당 뉴스에 달린 댓글의 개수를 반환합니다.
     * @param news_id
     * @return 해당 기사에 달린 댓글의 개수
     */
    @GetMapping("/user/comments/count/{news_id}")
    public ResponseEntity<Integer> getCommentCount(@PathVariable String news_id) {
        return ResponseEntity.ok().body(commentService.getCommentCount(news_id));
    }

    @GetMapping("/comments/profile/{username}/{currentUser}")
    public ResponseEntity<Page<CommentResponseDto>> getCommentsOnProfilePage(@PathVariable String username,
                                                                             @RequestParam int page,
                                                                             @RequestParam int size,
                                                                             @PathVariable String currentUser) {
        return ResponseEntity.ok().body(commentService.getCommentsByUserId(username, (page-1), size, currentUser));
    }
}
