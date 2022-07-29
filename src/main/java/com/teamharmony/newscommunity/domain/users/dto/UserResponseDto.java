package com.teamharmony.newscommunity.domain.users.dto;

import com.teamharmony.newscommunity.domain.users.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDto {
	private String username;
	
	public UserResponseDto(User user) {
		this.username = user.getUsername();
	}
	
	public static UserResponseDto toDto(User user) {
		return new UserResponseDto(user);
	}
}