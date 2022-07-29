package com.teamharmony.newscommunity.domain.auth.repository;


import com.teamharmony.newscommunity.domain.auth.entity.Tokens;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokensRepository extends JpaRepository<Tokens, Long> {
	Tokens findByUsername(String username);
}
