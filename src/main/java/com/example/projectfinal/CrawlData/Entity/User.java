package com.example.projectfinal.CrawlData.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "account_id", nullable = false)
    private Integer accountId;

    @Column(name = "reputation", nullable = false)
    private Integer reputation;

    @Column(name = "user_type", nullable = false)
    private String userType;

    @Column(name = "display_name", nullable = false)
    private String displayName;

    @Column(name = "profile_image", nullable = false)
    private String profileImage;

    @Column(name = "link", nullable = false)
    private String link;
}