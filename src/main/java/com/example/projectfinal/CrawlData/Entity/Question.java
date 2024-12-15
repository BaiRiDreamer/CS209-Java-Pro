package com.example.projectfinal.CrawlData.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
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
}