package com.teamharmony.newscommunity.domain.auth.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.teamharmony.newscommunity.domain.auth.config.security.AuthConstants;
import com.teamharmony.newscommunity.domain.auth.entity.Tokens;
import com.teamharmony.newscommunity.domain.auth.repository.TokensRepository;
import com.teamharmony.newscommunity.exception.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

// 사용자 권한 확인
@RequiredArgsConstructor
public class CustomAuthorizationFilter extends OncePerRequestFilter {
	private final UserDetailsService userDetailsService;
	private final TokensRepository tokensRepository;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		// 인가 과정을 거칠 필요가 없는 요청
		if (request.getServletPath().equals("/api/login") ||
				request.getServletPath().startsWith("/api/signup") ||
				request.getServletPath().equals("/api/token/refresh")) {
			filterChain.doFilter(request, response);
		} else {
			// 접근 토큰이 있으면 요청에 필요한 권한이 있는지 확인
			String authorizationHeader = request.getHeader(AUTHORIZATION);
			if (authorizationHeader != null && authorizationHeader.startsWith(AuthConstants.TOKEN_TYPE)) {
				try {
					String access_token = authorizationHeader.substring(AuthConstants.TOKEN_TYPE.length());
					Algorithm algorithm = Algorithm.HMAC256("secretKey".getBytes());
					JWTVerifier verifier = JWT.require(algorithm).build();
					DecodedJWT decodedJWT = verifier.verify(access_token);
					String username = decodedJWT.getSubject();
					
					// 해당 유저의 허용된 토큰값과 비교
					Tokens tokens = tokensRepository.findByUsername(username);
					if (tokens == null)
						throw AuthException.builder().message("허용 토큰 정보를 찾을 수 없습니다.").invalidValue("사용자 ID: " + username).code("A403").build();
					String allowedToken = tokens.getAccessToken();
					if(!allowedToken.equals(access_token))
						throw AuthException.builder().message("허용된 접근 토큰이 아닙니다.").invalidValue("접근 토큰: " + access_token).code("A404").build();
				
					String[] roles = decodedJWT.getClaim("roles")
																		 .asArray(String.class);
					Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
					stream(roles).forEach(role -> {authorities.add(new SimpleGrantedAuthority(role));});
					User user = (User) userDetailsService.loadUserByUsername(username);
					UsernamePasswordAuthenticationToken authenticationToken =
							new UsernamePasswordAuthenticationToken(user, null, authorities);
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
					filterChain.doFilter(request, response);
				} catch (TokenExpiredException e) {
					throw AuthException.builder().message("접근 토큰이 만료되었습니다.").code("A406").build();
				} catch (JWTVerificationException e) {
					throw AuthException.builder().message("올바른 토큰이 아닙니다.").code("A402").build();
				} catch (AuthException e) {
					throw AuthException.builder().message(e.getMessage()).code(e.getCode()).build();
				}
			} else {
				filterChain.doFilter(request, response);
			}
		}
	}
}