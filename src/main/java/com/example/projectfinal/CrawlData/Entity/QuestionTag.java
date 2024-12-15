package com.example.projectfinal.CrawlData.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "question_tags")
@IdClass(QuestionTagId.class)
public class QuestionTag {
    @Id
    @Column(name = "question_id")
    private Integer questionId;

    @Id
    @Column(name = "tag_id")
    private Integer tagId;

    // Getter and Setter methods
    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }
}