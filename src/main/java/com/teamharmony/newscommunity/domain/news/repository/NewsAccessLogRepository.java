package com.teamharmony.newscommunity.domain.news.repository;

import com.teamharmony.newscommunity.domain.news.entity.NewsAccessLog;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NewsAccessLogRepository extends JpaRepository<NewsAccessLog, String> {
}