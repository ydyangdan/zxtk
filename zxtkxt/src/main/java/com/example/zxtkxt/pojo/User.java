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
 * 用户信息
 */
@Data
@Repository
@Validated
@TableName("sys_user")
public class User {

    /**
     * ID
     */
    @TableId(value = "user_id",type = IdType.AUTO)
    private int userId;

    /**
     * 用户名
     */
    @TableField("user_name")
//    @NotEmpty(message = "用户名不能为空")
//    @Length(max = 11,min = 3,message = "用户名必需在{max}与{min}之间")
    private String userName;


    /**
     * 密码
     */
    private String password;

    /**
     * 密码盐
     */
    private String salt;

    /**
     * 用户身份
     */
    @TableField("user_role")
    private String userRole;

    /**
     * 联系方式
     */
    @TableField("contact")
    private String contact;

    /**
     * 用户性别
     */
    @TableField("user_sex")
    private String userSex;

    /**
     * 用户年龄
     */
    @TableField("user_age")
    private String userAge;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 最新登录时间
     */
    @TableField("latest_login_time")
    private Date latestLoginTime;


}
