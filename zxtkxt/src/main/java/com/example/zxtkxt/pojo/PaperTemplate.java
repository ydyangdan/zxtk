package com.example.zxtkxt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * 试卷模板
 */
@Data
@Repository
public class PaperTemplate {

    /**
     * 试卷模板编号
     */
    @TableId(value = "template_id",type = IdType.AUTO)
    private int templateId;

    /**
     * 试卷名称
     */
    @TableField(exist = false)
    private String paperName;

    /**
     * 单选题数量
     */
    private int choiceCount;

    /**
     * 单选题分数
     */
    private int choiceScore;

    /**
     * 多选题数量
     */
    private int multiCount;

    /**
     * 多选题分数
     */
    private int multiScore;

    /**
     * 填空题数量
     */
    private int blankFillingCount;

    /**
     * 填空题分数
     */
    private int blankFillingScore;

    /**
     * 判断题数量
     */
    private int judgeCount;

    /**
     * 判断题分数
     */
    private int judgeScore;

    /**
     * 简答题数量
     */
    private int shortCount;

    /**
     * 简答题分数
     */
    private int shortScore;

    /**
     * 知识点覆盖度
     */
    private double pointCoverage;

    /**
     * 模板所有者
     */
    private String userName;

    /**
     * 试卷难度
     */
    private String paperLevel;

    /**
     *试卷相似度
     */
    private double repeatability;

    /**
     * 组卷方式
     */
    @TableField(exist = false)
    private String autoType;

    /**
     *课程名称
     */
    @TableField(exist = false)
    private String courseName;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    public double getDifficulty() {

        if ("容易".equals(this.paperLevel)){
            return 1.0;
        }else  if ("较易".equals(this.paperLevel)){
            return 2.0;
        }else  if ("中等".equals(this.paperLevel)){
            return 3.0;
        }else  if ("较难".equals(this.paperLevel)){
            return 4.0;
        }else {
            return 5.0;
        }
    }


}
