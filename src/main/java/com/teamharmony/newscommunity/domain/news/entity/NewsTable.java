package com.teamharmony.newscommunity.domain.news.entity;


import lombok.*;
import org.springframework.boot.web.servlet.ServletComponentScan;

import javax.persistence.*;


/**
 * 파이썬 뉴스 정보 관리 외부 모듈로부터 RDS에 저장된 NewsTable을 연동하기 위한 엔티티
 * @Author hyeoKing
 */
@Getter @Setter //  NewsServiceTest 테스트 코드에서 의도적 값 수정을 위해 어쩔 수 없이 Setter 추가
@NoArgsConstructor
@Entity
@Table(indexes = @Index(name= "u_NewsId", columnList = "id"))
public class NewsTable {
    @Id
    @Column(nullable = false, unique = true, length = 30)
    private String id;              // 뉴스의 id 정보: Oauth 값을 String 형태로 변환

    @Column(nullable = false, length = 60)
    private String title;           // 뉴스의 제목 정보

    @Column(nullable = false, length = 1000)
    private String summary;         // python 외부 모듈에서 요약된 뉴스 요약 정보,

    @Column(nullable = false, length = 200)
    private String image_url;       // html 상의 이미지 표현을 위한 뉴스의 이미지가 저장된 image_url

    @Column(nullable = false, length = 200)
    private String news_url;        // 해당 뉴스의 원본 주소 (네이버 뉴스의 해당 뉴스 주소)

    @Column(nullable = false, length = 100)
    private String explains;         // 뉴스에 대한 설명

    @Column(nullable = false, length = 30)
    private String write_time;      // 작성 시간

    @Column(nullable = false)
    private Long view;               // 조회수

    public void updateView() {this.view += 1;}  // DB에 대한 조회수 증가 요청을 처리할 함수

    @Builder
    public NewsTable(String id, String title, String summary, String image_url, String news_url, String explains, String write_time, Long view){
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.image_url = image_url;
        this.news_url = news_url;
        this.explains = explains;
        this.write_time = write_time;
        this.view = view;
    }
}

