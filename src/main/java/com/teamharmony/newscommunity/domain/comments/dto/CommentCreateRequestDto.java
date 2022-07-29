package com.teamharmony.newscommunity.domain.comments.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CommentCreateRequestDto {

    @Size(max = 300, message = "글자수 초과")
    @ApiModelProperty(value = "댓글 내용", example = "기사가 흥미롭네요", required = true)
    private String content;

    @ApiModelProperty(value = "뉴스 아이디", example = "05c0a832-e8c3-4f16-8792-5c3778", required = true)
    private String newsId;
}
