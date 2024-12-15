package com.example.projectfinal.CrawlData.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
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
}