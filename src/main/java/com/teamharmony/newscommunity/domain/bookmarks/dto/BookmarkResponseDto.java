package com.teamharmony.newscommunity.domain.bookmarks.dto;

import com.teamharmony.newscommunity.domain.bookmarks.entity.Bookmarks;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;


@Getter
public class BookmarkResponseDto {
    @ApiModelProperty(value = "북마크 id", example = "test_uuid", required = true)
    String id;          // 북마크 식별을 위한 id
    @ApiModelProperty(value = "뉴스 식별 id", example = "02c1e768-a5c0-4958-aa44-130512", required = true)
    String newsId;      // 사용자가 접근한 newsId
    @ApiModelProperty(value = "유저 식별 id", example = "chlcksgur1", required = true)
    String userId;      // 해당 뉴스에 접근한 userId
    @ApiModelProperty(value = "제목", example = "아데산야 충격적인 패배", required = true)
    String title;

    @Builder
    public BookmarkResponseDto(String id, String newsId, String userId, String title){
        this.id = id;
        this.newsId = newsId;
        this.userId = userId;
        this.title = title;
    }
    public static BookmarkResponseDto toDto(Bookmarks bookmarks) { return BookmarkResponseDto.builder().id(bookmarks.getNewsId())
                                                                                                        .newsId(bookmarks.getNewsId())
                                                                                                        .userId(bookmarks.getUserId())
                                                                                                        .title(bookmarks.getTitle())
                                                                                                        .build();}
}
