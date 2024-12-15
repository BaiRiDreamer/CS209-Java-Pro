package com.example.projectfinal.CrawlData.Repository;

import com.example.projectfinal.CrawlData.Entity.QuestionTag;
import com.example.projectfinal.CrawlData.Entity.QuestionTagId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionTagRepository extends JpaRepository<QuestionTag, QuestionTagId> {
}