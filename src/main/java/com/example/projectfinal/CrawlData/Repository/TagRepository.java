package com.example.projectfinal.CrawlData.Repository;

import com.example.projectfinal.CrawlData.Entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    Tag findByName(String name);
}