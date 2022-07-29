package com.teamharmony.newscommunity.domain.auth.controller;

import com.teamharmony.newscommunity.domain.auth.service.AuthService;
import com.teamharmony.newscommunity.common.annotation.CurrentUser;
import com.teamharmony.newscommunity.exception.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthController {
	private final AuthService authService;
	
	/**
	 * 접근 토큰, 갱신 토큰 갱신
	 *
	 * @return 성공 확인
	 * @see AuthService#refreshToken
	 */
	@GetMapping("/token/refresh")
	public ResponseEntity<String> refreshToken(HttpServletRequest request, HttpServletResponse response) throws AuthException {
		return ResponseEntity.ok().body(authService.refreshToken(request, response));
	}
	
	/**
	 * 로그아웃
	 *
	 * @param username 인증된 사용자 ID
	 * @see AuthService#signOut
	 */
	@GetMapping("/user/signout")
	public ResponseEntity<String> signOut(HttpServletRequest request, HttpServletResponse response, @CurrentUser String username) throws AuthException {
		return ResponseEntity.ok().body(authService.signOut(request, response, username));
	}
}
