package com.teamharmony.newscommunity.domain.users.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.teamharmony.newscommunity.common.util.Timestamped;
import com.teamharmony.newscommunity.domain.comments.entity.Comment;
import com.teamharmony.newscommunity.domain.comments.entity.Likes;
import com.teamharmony.newscommunity.domain.supports.entity.Support;
import com.teamharmony.newscommunity.domain.users.dto.SignupRequestDto;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class User  extends Timestamped {
	@JsonIgnore
	@Id @GeneratedValue(strategy = IDENTITY)
	@Column(name = "user_id")
	private Long id;
	@Column(unique = true, nullable = false)
	@Pattern(regexp = "(?=.*[a-zA-Z])[-a-zA-Z0-9_.]{2,10}")
	private String username;
	@Column(nullable = false)
	@NotBlank
	private String password;

	@OneToMany(mappedBy="user", cascade = CascadeType.ALL)
	private Collection<UserRole> userRoles = new ArrayList<>();

	@JsonBackReference
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Comment> comments;

	@JsonBackReference
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Likes> likesList;

	@JsonBackReference
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Support> supports;

	public void addComment(Comment comment) {
		comment.setUser(this);
		comments.add(comment);
	}

	public void addLikes(Likes likes) {
		likes.setUser(this);
		likesList.add(likes);
	}
	public void addSupports(Support support) {
		support.setUser(this);
		supports.add(support);
	}

	@JsonManagedReference
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private UserProfile profile;

	public User(SignupRequestDto dto) {
		this.username = dto.getUsername();
		this.password = dto.getPassword();
	}
	
	public void encodePW(String encodedPW) {
		this.password = encodedPW;
	}
}
