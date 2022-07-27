package com.teamharmony.newscommunity.users.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class UserRole {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;
	
	public UserRole(User user, Role role) {
		this.user = user;
		this.role = role;
	}
}
