package com.teamharmony.newscommunity.domain.users.controller;

import com.teamharmony.newscommunity.common.annotation.CurrentUser;
import com.teamharmony.newscommunity.domain.users.dto.ProfileRequestDto;
import com.teamharmony.newscommunity.domain.users.dto.UserResponseDto;
import com.teamharmony.newscommunity.domain.users.entity.Role;
import com.teamharmony.newscommunity.domain.users.entity.RoleType;
import com.teamharmony.newscommunity.domain.users.service.UserService;
import com.teamharmony.newscommunity.domain.users.dto.SignupRequestDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * 사용자 관련 요청을 처리
 *
 * @author enyo9rt
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {
	private final UserService userService;

	/**
	 * 회원 ID 중복 확인
	 *
	 * @param requestDto 가입하려는 사용자 ID
	 * @return 중복 여부
	 * @see UserService#checkUser
	 */
	@PostMapping("/signup/checkdup")
	public ResponseEntity<Map<String, Boolean>> checkUser(@RequestBody @Valid SignupRequestDto.CheckDup requestDto) {
		return ResponseEntity.ok().body(userService.checkUser(requestDto.username));
	}

	/**
	 * 회원 가입
	 *
	 * @param requestDto 가입하려는 사용자 ID와 비밀번호를 담은 객체
	 * @return 성공 확인
	 * @see UserService#signUp
	 */
	@PostMapping("/signup")
	public ResponseEntity<String> signUp(@RequestBody @Valid SignupRequestDto requestDto) {
		return ResponseEntity.ok().body(userService.signUp(requestDto));
	}

	/**
	 * 현재 로그인한 유저 ID
	 *
	 * @param username 인증된 사용자 정보
	 * @return 인증된 사용자 ID
	 * @see UserDetails#getUsername
	 */
	@GetMapping("/user/me")
	public ResponseEntity<String> getUsername(@CurrentUser String username) {
		return ResponseEntity.ok().body(username);
	}

	/**
	 * 권한 추가
	 *
	 * @param 		role enum 타입 권한명이 담긴 객체
	 * @see				UserService#saveRole
	 */
	@PostMapping("/admin/role/save")
	public ResponseEntity<Void> saveUser(@RequestBody Role role) {
		userService.saveRole(role);
		return ResponseEntity.ok().build();
	}

	/**
	 * 사용자 정보에 권한 추가
	 *
	 * @param form 사용자 ID와 권한명이 담긴 객체
	 * @see UserService#addRoleToUser
	 */
	@PostMapping("/admin/role/addtouser")
	public ResponseEntity<Void> addRoleToUser(@RequestBody RoleToUserForm form) {
		userService.addRoleToUser(form.getUsername(), form.getRoleName());
		return ResponseEntity.ok().build();
	}

	/**
	 * 전체 유저 불러오기
	 *
	 * @return 전체 유저를 담은 List
	 * @see UserService#getUsers
	 */
	@GetMapping("/admin/users")
	public ResponseEntity<List<UserResponseDto>> getUsers() {
		return ResponseEntity.ok().body(userService.getUsers());
	}

	/**
	 * 회원 프로필 정보 조회
	 *
	 * @param profileOwner 프로필 회원 ID
	 * @param username 인증된 사용자 정보
	 * @return 인증된 사용자 ID와 일치 여부, 프로필 사진 url, 프로필 정보
	 * @see UserService#getProfile
	 */
	@GetMapping("/user/profile/{profileOwner}")
	public ResponseEntity<Map<String, Object>> getProfile(@PathVariable String profileOwner, @CurrentUser String username) {
		boolean status = profileOwner.equals(username);
		return ResponseEntity.ok().body(userService.getProfile(profileOwner, status));
	}
	
	/**
	 * 회원 프로필 사진 URL 조회
	 *
	 * @param username 프로필 회원 ID
	 * @return 프로필 사진 url
	 * @see UserService#getProfileImageUrl
	 */
	@GetMapping("/user/profile/pic/{username}")
	public ResponseEntity<String> getProfilePicUrl(@PathVariable String username) {
		return ResponseEntity.ok().body(userService.getProfileImageUrl(username));
	}
	
	/**
	 * 회원 프로필 변경
	 *
	 * @param requestDto 닉네임, 프로필 사진 파일, 소개글을 담은 객체
	 * @param username 인증된 사용자 정보
	 * @return 성공 확인, 메시지
	 * @see UserService#updateProfile
	 */
	@PostMapping(
			path = "/user/update_profile",
			consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<Map<String, String>> updateProfile(ProfileRequestDto requestDto, @CurrentUser String username) {
		return ResponseEntity.ok().body(userService.updateProfile(username, requestDto));
	}

	@Data
	class RoleToUserForm{
		private String username;
		private RoleType roleName;
	}
}