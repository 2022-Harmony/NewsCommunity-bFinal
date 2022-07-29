package com.teamharmony.newscommunity.domain.users.repository;

import com.teamharmony.newscommunity.domain.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
