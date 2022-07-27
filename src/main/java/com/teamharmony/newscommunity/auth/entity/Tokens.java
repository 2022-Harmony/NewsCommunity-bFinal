package com.teamharmony.newscommunity.auth.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Pattern;

import static javax.persistence.GenerationType.IDENTITY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class Tokens {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long id;
	@Column(unique = true, nullable=false)
	@Pattern(regexp = "(?=.*[a-zA-Z])[-a-zA-Z0-9_.]{2,10}")
	private String username;
	@Column(nullable = false)
	private String accessToken;
	@Column(nullable = false)
	private String refreshToken;
	
	@Builder
	public Tokens(String username, String accessToken, String refreshToken) {
		this.username = username;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
	
	public void update(String access_token, String refresh_token) {
		this.accessToken = access_token;
		this.refreshToken = refresh_token;
	}
}
