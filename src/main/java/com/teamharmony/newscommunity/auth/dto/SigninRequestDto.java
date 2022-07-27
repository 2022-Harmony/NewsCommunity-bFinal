package com.teamharmony.newscommunity.auth.dto;

import lombok.Getter;

import javax.validation.constraints.Pattern;

import static com.teamharmony.newscommunity.auth.util.SigninValidator.isValid;

@Getter
public class SigninRequestDto {
	@Pattern(regexp = "^(?=.*[a-zA-Z])[-a-zA-Z0-9_.]{2,10}$")
	private String username;
	@Pattern(regexp = "^(?=.*\\d)(?=.*[a-zA-Z])[0-9a-zA-Z!@#$%^&*]{8,20}$")
	private String password;
	
	public SigninRequestDto(String username, String password) {
		isValid(username, password);
		this.username = username;
		this.password = password;
	}
}