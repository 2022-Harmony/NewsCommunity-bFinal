package com.teamharmony.newscommunity.users.dto;

import lombok.Getter;

import javax.validation.constraints.Pattern;

@Getter
public class SignupRequestDto {
	@Pattern(regexp = "(?=.*[a-zA-Z])[-a-zA-Z0-9_.]{2,10}")
	private String username;
	@Pattern(regexp = "(?=.*\\d)(?=.*[a-zA-Z])[0-9a-zA-Z!@#$%^&*]{8,20}")
	private String password;
	
	public static class CheckDup {
		@Pattern(regexp = "(?=.*[a-zA-Z])[-a-zA-Z0-9_.]{2,10}")
		public String username;
	}
}
