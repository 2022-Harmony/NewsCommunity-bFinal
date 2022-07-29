package com.teamharmony.newscommunity.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthException extends RuntimeException {
	private String invalidValue;
	private String code;

	@Builder
	public AuthException(String message, String invalidValue, String code) {
		super(message);
		this.invalidValue = invalidValue;
		this.code = code;
	}
}
