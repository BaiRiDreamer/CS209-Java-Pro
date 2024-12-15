package com.example.projectfinal.CrawlData.Repository;

import com.example.projectfinal.CrawlData.Entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
}