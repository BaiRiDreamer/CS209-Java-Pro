package com.example.projectfinal.CrawlData.Entity;

import lombok.Data;
import java.io.Serializable;

@Data
public class QuestionTagId implements Serializable {
    private Integer questionId;
    private Integer tagId;
}