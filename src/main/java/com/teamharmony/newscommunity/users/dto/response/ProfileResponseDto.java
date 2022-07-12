package com.teamharmony.newscommunity.users.dto.response;

import com.teamharmony.newscommunity.users.entity.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
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
