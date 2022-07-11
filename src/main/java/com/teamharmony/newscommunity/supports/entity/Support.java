package com.teamharmony.newscommunity.supports.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.teamharmony.newscommunity.supports.dto.SupportRequestDto;
import com.teamharmony.newscommunity.supports.dto.SupportRequestUpdateDto;
import com.teamharmony.newscommunity.users.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Support extends Timestamped{

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long support_id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String post_title;

    @Column(nullable = false)
    private String post_content;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Support(String username, String post_title, String post_content) {
        this.username = username;
        this.post_title = post_title;
        this.post_content = post_content;
    }

    public Support(SupportRequestDto requestedDto) {
        this.post_title = requestedDto.getPost_title();
        this.post_content = requestedDto.getPost_content();
    }
    public Support(SupportRequestDto requestedDto, String username) {
        this.username = username;
        this.post_title = requestedDto.getPost_title();
        this.post_content = requestedDto.getPost_content();
    }

    public void update(SupportRequestUpdateDto requestDto){
        this.post_content = requestDto.getPost_content();
    }

}
