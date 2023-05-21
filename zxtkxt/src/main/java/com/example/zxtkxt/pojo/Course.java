package com.example.zxtkxt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 *课程表
 */
@Data
@Repository
public class Course {
    /**
     * ID
     */
    @TableId(value = "course_id",type = IdType.AUTO)
    private int courseId;

    /**
     * 课程编号
     */
    private String courseNumber;

    /**
     *课程名称
     */
    private String courseName;

    /**
     *课程所有者
     */
    private String courseTeacher;

    /**
     *课程类型
     */
    private String courseType;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    
}
