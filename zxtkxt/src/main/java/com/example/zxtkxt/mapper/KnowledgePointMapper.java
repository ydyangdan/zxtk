package com.example.zxtkxt.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.zxtkxt.pojo.KnowledgePoint;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface KnowledgePointMapper extends BaseMapper<KnowledgePoint> {

    @Select("select point_name from  knowledge_point where course_name=#{courseName}")
    List<String> selectPointName(String courseName);

    @Select("select count(point_id) from  knowledge_point where course_name=#{courseName}")
    int selectCount(String courseName);
}
