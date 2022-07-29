package com.teamharmony.newscommunity.domain.supports.repository;

import com.teamharmony.newscommunity.domain.supports.entity.Support;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SupportRepository extends JpaRepository<Support, Long> {
    List<Support> findAllByOrderByCreatedAtDesc();
    List<Support> findAllByUsernameOrderByCreatedAtDesc(String username);
}

