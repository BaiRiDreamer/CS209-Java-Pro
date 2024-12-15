package com.example.projectfinal.CrawlData.Entity;

import jakarta.persistence.*;

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

    // Getter and Setter methods
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getReputation() {
        return reputation;
    }

    public void setReputation(Integer reputation) {
        this.reputation = reputation;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}