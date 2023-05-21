package com.example.zxtkxt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotEmpty;
import java.beans.Transient;
import java.util.Date;
/**
 *试卷表
 */
@Data
@Repository
public class Paper {
    /**
     * ID
     */
    @TableId(value = "paper_id",type = IdType.AUTO)
    private int paperId;

    /**
     * 试卷名称
     */
    private String paperName;

    /**
     *出题人
     */
    private String paperAuthor;

    /**
     *课程名称
     */
    private String courseName;

    /**
     *单选题
     */
    private String choiceQuestion = "";

    /**
     *多选题
     */
    private String multiQuestion = "";

    /**
     *填空题
     */
    private String blankFillingQuestion = "";

    /**
     *判断题
     */
    private String judgeQuestion = "";

    /**
     *简答题
     */
    private String shortQuestion = "";
    /**
     * 单选题分数
     */
    private int choiceScore;

    /**
     * 多选题分数
     */
    private int multiScore;


    /**
     * 填空题分数
     */
    private int blankFillingScore;


    /**
     * 判断题分数
     */
    private int judgeScore;


    /**
     * 简答题分数
     */
    private int shortScore;


    /**
     * 知识点覆盖度
     */

    private double pointCoverage;

    /**
     *试卷重复度
     */
    private double repeatability = 0.0;


    /**
     * 试卷难度
     */
    private double paperLevel;

    /**
     * 组卷方式
     */
    @TableField(exist = false)
    private String autoType;



    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

}
