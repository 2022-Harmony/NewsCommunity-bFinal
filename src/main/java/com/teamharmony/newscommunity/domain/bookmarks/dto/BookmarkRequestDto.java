package com.teamharmony.newscommunity.domain.bookmarks.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookmarkRequestDto {
    @ApiModelProperty(value = "뉴스 식별 id", example = "02c1e768-a5c0-4958-aa44-130512", required = true)
    private String newsId;
    @ApiModelProperty(value = "유저 식별 id", example = "chlcksgur1", required = true)
    private String userId;
    @ApiModelProperty(value = "제목", example = "아데산야 충격적인 패배", required = true)
    private String title;
}
