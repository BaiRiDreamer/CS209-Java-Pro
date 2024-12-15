package com.example.projectfinal.CrawlData.Entity;

import java.io.Serializable;

public class QuestionTagId implements Serializable {
    private Integer questionId;
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