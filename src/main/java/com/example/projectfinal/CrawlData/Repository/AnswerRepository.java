package com.example.projectfinal.CrawlData.Repository;

import com.example.projectfinal.CrawlData.Entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
}