package com.example.projectfinal.CrawlData.Entity;


/*CREATE TABLE question_tags (
        question_id INT NOT NULL,  -- 问题的 ID，不能为空，外键关联到 'questions' 表
                tag_id INT NOT NULL,  -- 标签的 ID，不能为空，外键关联到 'tags' 表
                PRIMARY KEY (question_id, tag_id),  -- 多对多关系的主键，由问题 ID 和标签 ID 组成
FOREIGN KEY (question_id) REFERENCES questions(question_id) ON DELETE CASCADE,  -- 外键约束，关联 'questions' 表，删除问题时同时删除关联
FOREIGN KEY (tag_id) REFERENCES tags(tag_id) ON DELETE CASCADE  -- 外键约束，关联 'tags' 表，删除标签时同时删除关联
);*/


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "question_tags")
public class QuestionTag {
    @Id
    @Column(name = "question_id")
    private Integer questionId;

    @Id
    @Column(name = "tag_id")
    private Integer tagId;
}