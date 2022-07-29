package com.teamharmony.newscommunity.domain.auth.dto;

import com.teamharmony.newscommunity.domain.auth.util.SigninValidator;
import lombok.Getter;

import javax.validation.constraints.Pattern;

@Getter
public class SigninRequestDto {
	@Pattern(regexp = "^(?=.*[a-zA-Z])[-a-zA-Z0-9_.]{2,10}$")
	private String username;
	@Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z])[0-9a-zA-Z!@#$%^&*]{8,20}$")
	private String password;
	
	public SigninRequestDto(String username, String password) {
		SigninValidator.isValid(username, password);
		this.username = username;
		this.password = password;
	}
}