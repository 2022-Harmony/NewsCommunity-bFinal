package com.teamharmony.newscommunity.users.dto;

import com.teamharmony.newscommunity.users.entity.User;
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