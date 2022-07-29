package com.teamharmony.newscommunity.domain.supports.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SupportResponseDto {
    private long id;
    private String username;
    private String post_title;
    private String post_content;
    private String post_email;
    private LocalDateTime created_at;
    private LocalDateTime modified_at;

    @Builder
    public SupportResponseDto(Long id, String username, String post_title, String post_content, String post_email, LocalDateTime created_at, LocalDateTime modified_at) {
        this.id = id;
        this.username = username;
        this.post_title = post_title;
        this.post_content = post_content;
        this.post_email = post_email;
        this.created_at = created_at;
        this.modified_at = modified_at;
    }
}