package com.example.projectfinal.CrawlData.Entity;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "questions")
public class Question {
    @Id
    @Column(name = "question_id")
    private Integer questionId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content_license")
    private String contentLicense;

    @Column(name = "link", nullable = false)
    private String link;

    @Column(name = "view_count", nullable = false)
    private Integer viewCount;

    @Column(name = "answer_count", nullable = false)
    private Integer answerCount;

    @Column(name = "score", nullable = false)
    private Integer score;

    @Column(name = "creation_date", nullable = false)
    private OffsetDateTime creationDate;

    @Column(name = "last_activity_date", nullable = false)
    private OffsetDateTime lastActivityDate;

    @Column(name = "owner_user_id", nullable = false)
    private Integer ownerUserId;

    @Column(name = "is_answered", nullable = false)
    private Boolean isAnswered;

    @Column(name = "accepted_answer_id")
    private Integer acceptedAnswerId;

    // Getter and Setter methods
    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContentLicense() {
        return contentLicense;
    }

    public void setContentLicense(String contentLicense) {
        this.contentLicense = contentLicense;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(Integer answerCount) {
        this.answerCount = answerCount;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
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

    public Integer getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(Integer ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public Boolean getIsAnswered() {
        return isAnswered;
    }

    public void setIsAnswered(Boolean isAnswered) {
        this.isAnswered = isAnswered;
    }

    public Integer getAcceptedAnswerId() {
        return acceptedAnswerId;
    }

    public void setAcceptedAnswerId(Integer acceptedAnswerId) {
        this.acceptedAnswerId = acceptedAnswerId;
    }
}