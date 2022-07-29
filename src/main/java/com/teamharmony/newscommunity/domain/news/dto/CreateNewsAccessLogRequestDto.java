package com.teamharmony.newscommunity.domain.news.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * NewsAccessLog에 대한 생성 요청을 위해 필요한 DTO
 * @Author Chanhyuk King, the Great East Developer's King
 */
@Getter
@NoArgsConstructor
public class CreateNewsAccessLogRequestDto {
    @ApiModelProperty(value = "뉴스 식별 id", example = "02c1e768-a5c0-4958-aa44-130512", required = true)
    private String news_id;
    @ApiModelProperty(value = "유저 식별 id", example = "chlcksgur1", required = true)
    private String user_id;
    @ApiModelProperty(value = "제목", example = "아데산야 충격적인 패배", required = true)
    private String title;

    /**
     * @param news_id 뉴스 식별을 위한 id
     * @param user_id 사용자 식별을 위한 id
     * @param title   식별 뉴스의 제목
     */
    @Builder
    public CreateNewsAccessLogRequestDto(String news_id, String user_id, String title){
        this.news_id = news_id;         // 테스트 코드에서의 객체 생성시, 실수를 줄이기 위해 @Builder 기입
        this.user_id = user_id;
        this.title = title;
    }
}