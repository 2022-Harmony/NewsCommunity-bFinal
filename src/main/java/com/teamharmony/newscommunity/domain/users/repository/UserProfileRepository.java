package com.teamharmony.newscommunity.domain.users.repository;

import com.teamharmony.newscommunity.domain.users.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
}
