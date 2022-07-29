package com.teamharmony.newscommunity.domain.users.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ProfileRequestDto {
	@NotBlank(message = "닉네임은 공백일 수 없습니다.")
	@Size(max = 10, message = "닉네임은 최대 10자까지 입력 가능합니다.")
	private String name;
	private MultipartFile file;
	@Size(max = 60, message = "소개글은 최대 60자까지 입력 가능합니다.")
	private String about;
}