package com.example.zxtkxt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.zxtkxt.pojo.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CourseMapper extends BaseMapper<Course> {

    @Select("select course_name from course where course_teacher=#{userName}")
    List<String> selectCourseName(String userName);
}
