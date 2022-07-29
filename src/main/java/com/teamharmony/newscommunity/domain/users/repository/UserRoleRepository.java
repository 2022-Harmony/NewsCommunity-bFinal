package com.teamharmony.newscommunity.domain.users.repository;

import com.teamharmony.newscommunity.domain.users.entity.User;
import com.teamharmony.newscommunity.domain.users.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
	Collection<UserRole> findByUser(User user);
}
