package com.teamharmony.newscommunity.domain.comments.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommentEditRequestDto {
    @Size(max = 300, message = "글자수 초과")
    @ApiModelProperty(value = "수정한 댓글 내용", example = "좋은 기사 감사합니다")
    private String content;
}
