package com.teamharmony.newscommunity.domain.news.dto;


import com.teamharmony.newscommunity.domain.news.entity.NewsTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;


/**
 * 뉴스 상세정보를 Response하기 위한 DTO
 * @author hyeoKing
 */
@Getter
public class NewsDetailResponseDto {
    @ApiModelProperty(value = "뉴스 식별 id", example = "02c1e768-a5c0-4958-aa44-130512", required = true)
    private String id;
    @ApiModelProperty(value = "뉴스 제목", example = "아데산야 충격의 KO패배는 거짓말이지롱~", required = true)
    private String title;           
    @ApiModelProperty(value = "뉴스 요약 정보", example = "아데산야는 충격적인 패배를 하지 않았다. 사실 이것은 글쓴이가 너를 속인것이다.", required = true)
    private String summary;
    @ApiModelProperty(value = "해당 뉴스의 이미지 url", example = "https://t1.daumcdn.net/cfile/tistory/246A2A34544518512C", required = true)
    private String image_url;
    @ApiModelProperty(value = "해당 뉴스의 url", example = "https://blog.naver.com/cksgurwkd12", required = true)
    private String news_url;
    @ApiModelProperty(value = "작성 시간", example = "2022-07-29", required = true)
    private String write_time;
    @ApiModelProperty(value = "조회수", example = "30", required = true)
    private Long view;
    @ApiModelProperty(value = "뉴스에 대한 간략한 설명", example = "아데 산야는 결국 패배하지 못..", required = true)
    private String explains;


    @Builder
    public NewsDetailResponseDto(String id, String title, String explains, String summary, String image_url, String news_url, String write_time, Long view){
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.explains = explains;
        this.image_url = image_url;
        this.news_url = news_url;
        this.write_time = write_time;
        this.view = view;
    }

    public static NewsDetailResponseDto toDto(NewsTable newsTable) {
        return NewsDetailResponseDto.builder()
            .id(newsTable.getId())
            .title(newsTable.getTitle())
            .summary(newsTable.getSummary())
            .image_url(newsTable.getImage_url())
            .news_url(newsTable.getNews_url())
            .write_time(newsTable.getWrite_time())
            .explains(newsTable.getExplains())
            .view(newsTable.getView())
            .build();}

}
