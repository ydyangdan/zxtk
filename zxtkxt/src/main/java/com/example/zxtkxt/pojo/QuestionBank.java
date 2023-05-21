package com.example.zxtkxt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
/**
 * 题库
 */
@Data
@Repository
public class QuestionBank {

    /**
     * ID
     */
    @TableId(value = "bank_id",type = IdType.AUTO)
    private int bankId;

    /**
     * 题库名称
     */
    private String bankName;

    /**
     * 题库备注
     */
    private String bankRemark;


    /**
     * 单选题数量
     */
    private int choiceCount;

    /**
     * 多选题数量
     */
    private int multiCount;

    /**
     * 填空题数量
     */
    private int blankFillingCount;

    /**
     * 判断题数量
     */
    private int judgeCount;

    /**
     * 简答题数量
     */
    private int shortCount;

    /**
     * 题库所有者
     */
    private String userName;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;


}
