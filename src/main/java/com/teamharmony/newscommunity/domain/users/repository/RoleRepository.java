package com.teamharmony.newscommunity.domain.users.repository;


import com.teamharmony.newscommunity.domain.users.entity.Role;
import com.teamharmony.newscommunity.domain.users.entity.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByName(RoleType name);
}

