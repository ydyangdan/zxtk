package com.example.zxtkxt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * 知识点
 */
@Data
@Repository
public class KnowledgePoint {

    /**
     * 知识点编号
     */
    @TableId(value = "point_id",type = IdType.AUTO)
    private int pointId;

    /**
     * 知识点名称
     */
    private String pointName;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 知识点创建者
     */
    private String userName;

    /**
     * 知识点描述
     */
    private String pointDesc;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

}
