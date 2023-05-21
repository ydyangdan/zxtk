package com.example.zxtkxt.algorithm;

import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.List;

// 定义试题类，包括试题内容、难度系数和知识点列表
@Data
public class Question {
    private String content;
    private double difficulty;
    private List<String> knowledgePoints;

    public Question(String content, double difficulty, List<String> knowledgePoints) {
        this.content = content;
        this.difficulty = difficulty;
        this.knowledgePoints = knowledgePoints;
    }

    public Question() {

    }

    // 获取试题内容
    public String getContent() {
        return content;
    }

    // 获取难度系数
    public double getDifficulty() {
        return difficulty;
    }

    // 获取知识点列表
    public List<String> getKnowledgePoints() {
        return knowledgePoints;
    }


}