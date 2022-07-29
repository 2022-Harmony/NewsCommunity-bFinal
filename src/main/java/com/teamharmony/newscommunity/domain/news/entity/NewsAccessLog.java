package com.teamharmony.newscommunity.domain.news.entity;

import com.teamharmony.newscommunity.common.util.TimestampedOnLog;
import com.teamharmony.newscommunity.domain.news.dto.CreateNewsAccessLogRequestDto;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;


/**
 * 뉴스에 접근(클릭)에 관련된 기록을 남기기 위한 엔티티
 *
 *  @author : hyeoKing
 */
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(indexes = @Index(name= "u_NewsId", columnList = "id"))
public class NewsAccessLog extends TimestampedOnLog {
    @Id
    @Column(nullable = false)
    private String id;                      // 접근 기록에 대한 ID

    @Column(nullable = false)
    private String news_id;                 // 접근한 뉴스 ID

    @Column(nullable = false)
    private String user_id;                 // 해당 뉴스에 접근한 userId

    @Column(nullable = false)
    private String title;                   // 접근하 뉴스의 제목

    @Builder
    public NewsAccessLog(CreateNewsAccessLogRequestDto requestCreateNewsAccessLogDTO){
        this.id = UUID.randomUUID().toString();
        this.news_id = requestCreateNewsAccessLogDTO.getNews_id();
        this.user_id = requestCreateNewsAccessLogDTO.getUser_id();
        this.title = requestCreateNewsAccessLogDTO.getTitle();
    }
}
