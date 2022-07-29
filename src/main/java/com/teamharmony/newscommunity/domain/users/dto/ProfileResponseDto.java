package com.teamharmony.newscommunity.domain.users.dto;

import com.teamharmony.newscommunity.domain.users.entity.UserProfile;
import lombok.Getter;

@Getter
public class ProfileResponseDto {
	private String username;
	private String nickname;
	private String profile_pic;
	private String profile_info;
	
	public ProfileResponseDto(UserProfile profile) {
		this.username = profile.getUser().getUsername();
		this.nickname = profile.getNickname();
		this.profile_pic = profile.getProfile_pic();
		this.profile_info = profile.getProfile_info();
	}
	
	public static ProfileResponseDto toDto(UserProfile profile) {
		return new ProfileResponseDto(profile);
	}
}
