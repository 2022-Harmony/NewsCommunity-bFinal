package com.teamharmony.newscommunity.auth.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamharmony.newscommunity.auth.dto.SigninRequestDto;
import com.teamharmony.newscommunity.auth.entity.Tokens;
import com.teamharmony.newscommunity.auth.repository.TokensRepository;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.SET_COOKIE;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager authenticationManager;
	private final UserDetailsService userDetailsService;
	private final TokensRepository tokensRepository;
	
	public CustomAuthenticationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, TokensRepository tokensRepository) {
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
		this.tokensRepository = tokensRepository;
	}
	// 로그인 시도
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		UsernamePasswordAuthenticationToken authenticationToken = null;
		try {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			SigninRequestDto requestDto = new SigninRequestDto(username, password);
			User user = (User) userDetailsService.loadUserByUsername(requestDto.getUsername());
			authenticationToken = new UsernamePasswordAuthenticationToken(user, requestDto.getPassword());
		} catch (Exception e) {
			setError(response, e);
		}
		return authenticationManager.authenticate(authenticationToken);
	}
	// 로그인 성공
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
		User user = (User)authentication.getPrincipal();
		Algorithm algorithm = Algorithm.HMAC256("secretKey".getBytes());
		String access_token = JWT.create()
				.withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + 60*60*1000))
				.withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.sign(algorithm);
		String refresh_token = JWT.create()
				.withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + 7*24*60*60*1000))
				.sign(algorithm);
		
		String username = user.getUsername();
		Tokens existingTokens = tokensRepository.findByUsername(username);
		if (existingTokens==null) {
			// 유저가 토큰 정보를 가지고 있지 않으면 생성 후 DB 저장
			try {
				Tokens newTokens = Tokens.builder()
				                             .username(username)
				                             .accessToken(access_token)
				                             .refreshToken(refresh_token)
				                             .build();
				tokensRepository.save(newTokens);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			// 유저가 토큰 정보를 가지고 있으면 변경 후 DB 저장
			existingTokens.update(access_token, refresh_token);
			tokensRepository.save(existingTokens);
		}
		
		byte[] usernameHeader = username.getBytes(StandardCharsets.UTF_8);
		response.setHeader("token", access_token);
		response.setHeader("username", Base64.getEncoder().encodeToString(usernameHeader));
		ResponseCookie refresh = ResponseCookie.from("ref_uid", refresh_token)
		                                       .maxAge(7*24*60*60)
		                                       .httpOnly(true)
			                                     .secure(true)
						                               .sameSite("None")
				                                   .path("/")
				                                   .build();
		response.setHeader(SET_COOKIE, refresh.toString());
		
		response.setContentType(APPLICATION_JSON_VALUE);
		new ObjectMapper().writeValue(response.getOutputStream(), "success");
	}
	private static void setError(HttpServletResponse response, Exception e) {
		response.setHeader("error", e.getMessage());
		response.setStatus(FORBIDDEN.value());
		Map<String, String> error = new HashMap<>();
		error.put("error_msg", e.getMessage());
		response.setContentType(APPLICATION_JSON_VALUE);
		try {
			new ObjectMapper().writeValue(response.getOutputStream(), error);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
}
