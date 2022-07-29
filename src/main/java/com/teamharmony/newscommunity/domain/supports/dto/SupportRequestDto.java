package com.teamharmony.newscommunity.domain.supports.dto;

import lombok.Getter;

import javax.validation.constraints.Email;

@Getter
public class SupportRequestDto {
    private String post_title;
    private String post_content;
    @Email
    private String post_email;
}
