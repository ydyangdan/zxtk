package com.example.zxtkxt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Repository
public class Logs {
    /**
     * ID
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 试卷名称
     */
    private String paperName;

    /**
     * 组卷方式
     */
    private String autoType;

    /**
     * 组卷时长
     */
    private Double paperDuration;

    /**
     * 适应度
     */
    private Double fitness;

    /**
     * 实际难度
     */
    private Double difficulty;

    /**
     * 实际知识点覆盖度
     */
    private Double pointCoverage;

    /**
     * 实际相似度
     */
    private Double repeatability;

    /**
     * 目标难度
     */
    private Double targetDifficulty;

    /**
     * 目标知识点覆盖度
     */
    private Double targetPointCoverage;

    /**
     * 目标相似度
     */
    private Double targetRepeatability;

    /**
     * 操作时间
     */
    private Date operateTime;

}
