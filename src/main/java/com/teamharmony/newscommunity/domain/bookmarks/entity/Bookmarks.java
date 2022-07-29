package com.teamharmony.newscommunity.domain.bookmarks.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(indexes = @Index(name= "u_NewsId", columnList = "id"))
public class Bookmarks extends TimestampedOnBookmark {
    @Id
    @Column(nullable = false, unique = true, length = 60)
    String id;          // 북마크 식별을 위한 id

    @Column(nullable = false, length=70)
    String newsId;      // 사용자가 접근한 newsId

    @Column(nullable = false, length = 50)
    String userId;      // 해당 뉴스에 접근한 userId

    @Column(nullable = false, length = 50)
    String title;

    @Builder
    public Bookmarks(String newsId, String userId, String title){
        this.id = UUID.randomUUID().toString();
        this.newsId = newsId;
        this.userId = userId;
        this.title = title;
    }
}
