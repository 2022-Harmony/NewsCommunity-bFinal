package com.teamharmony.newscommunity.auth.util;


public class SigninValidator {
	public static void isValid(String username, String password) throws IllegalArgumentException {
		if (!username.matches("^(?=.*[a-zA-Z])[-a-zA-Z0-9_.]{2,10}$")) {
			throw new IllegalArgumentException("올바르지 않은 아이디입니다.");
		} else if (!password.matches("^(?=.*\\d)(?=.*[a-zA-Z])[0-9a-zA-Z!@#$%^&*]{8,20}$")) {
			throw new IllegalArgumentException("올바르지 않은 비밀번호입니다.");
		}
	}
}