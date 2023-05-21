package com.example.zxtkxt.pojo;

import lombok.Data;
import org.springframework.stereotype.Repository;

@Repository
@Data
public class QuestionSearch {

    /**
     *课程类型
     */
    private String courseType;

    /**
     * 试题类型
     */
    private String questionType;

    /**
     *题干
     */
    private String questionSubject;

    /**
     * 题库名称
     */
    private String bankName;


}
