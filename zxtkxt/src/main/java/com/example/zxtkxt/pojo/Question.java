package com.example.zxtkxt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.Date;
/**
 *试题表
 */
@Data
@Repository
public class Question {
    /**
     * ID
     */
    @TableId(value = "question_id",type = IdType.AUTO)
    private int questionId;

    /**
     *'难度级别'
     */
    private String questionLevel;

    /**
     *课程类型
     */
    private String courseType;


    /**
     *知识点
     */
    private String questionPoint;

    /**
     * 试题类型
     */
    private String questionType;


    /**
     *题干
     */
    private String questionSubject;

    /**
     *答案
     */
    private String questionAnswer;

    /**
     *选项A
     */
    private String optionA;

    /**
     *选项B
     */
    private String optionB;

    /**
     *选项C
     */
    private String optionC;

    /**
     *选项D
     */
    private String optionD;


    /**
     *图片
     */
    private String questionImage;

    /**
     *描述
     */
    private String questionDesc;

    /**
     * 题库名称
     */
    private String bankName;


    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    public double getDifficulty() {

        if ("容易".equals(this.questionLevel)){
            return 1.0;
        }else  if ("较易".equals(this.questionLevel)){
            return 2.0;
        }else  if ("中等".equals(this.questionLevel)){
            return 3.0;
        }else  if ("较难".equals(this.questionLevel)){
            return 4.0;
        }else {
            return 5.0;
        }
    }

}
