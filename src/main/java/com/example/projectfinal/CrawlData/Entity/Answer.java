package com.example.projectfinal.CrawlData.Entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "answers")
public class Answer {
    @Id
    @Column(name = "answer_id")
    private Integer answerId;

    @Column(name = "question_id", nullable = false)
    private Integer questionId;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "is_accepted", nullable = false)
    private Boolean isAccepted;

    @Column(name = "score", nullable = false)
    private Integer score;

    @Column(name = "body", nullable = false)
    private String body;

    @Column(name = "creation_date", nullable = false)
    private OffsetDateTime creationDate;

    @Column(name = "last_activity_date", nullable = false)
    private OffsetDateTime lastActivityDate;

    // Getter and Setter methods
    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getIsAccepted() {
        return isAccepted;
    }

    public void setIsAccepted(Boolean isAccepted) {
        this.isAccepted = isAccepted;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public OffsetDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(OffsetDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public OffsetDateTime getLastActivityDate() {
        return lastActivityDate;
    }

    public void setLastActivityDate(OffsetDateTime lastActivityDate) {
        this.lastActivityDate = lastActivityDate;
    }
}