package com.teamharmony.newscommunity.domain.news.repository;

import com.teamharmony.newscommunity.domain.news.entity.NewsTable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NewsRepository extends JpaRepository<NewsTable, String> {
}

