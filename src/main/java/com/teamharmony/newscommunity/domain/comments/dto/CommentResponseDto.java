package com.teamharmony.newscommunity.domain.comments.dto;

import com.teamharmony.newscommunity.domain.comments.entity.Comment;
import com.teamharmony.newscommunity.domain.users.dto.ProfileResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private Long commentId;
    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;
    private String content;
    private ProfileResponseDto profileResponseDto;
    private Boolean like;



    @Builder
    public CommentResponseDto(Long commentId,
                              LocalDateTime modifiedAt,
                              LocalDateTime createdAt,
                              String content,
                              ProfileResponseDto profileResponseDto,
                              Boolean like) {

        this.commentId = commentId;
        this.modifiedAt = modifiedAt;
        this.createdAt = createdAt;
        this.content = content;
        this.profileResponseDto = profileResponseDto;
        this.like = like;
    }

    public static CommentResponseDto toDto(Comment comment) {
        return CommentResponseDto.builder()
                .commentId(comment.getCommentId())
                .modifiedAt(comment.getModifiedAt())
                .createdAt(comment.getCreatedAt())
                .content(comment.getContent())
                .profileResponseDto(new ProfileResponseDto(comment.getUser().getProfile()))
                .build();
    }

    public void likeUser(Boolean like) {
        this.like = like;
    }
}
