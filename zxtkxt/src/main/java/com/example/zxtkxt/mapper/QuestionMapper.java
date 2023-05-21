package com.example.zxtkxt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.zxtkxt.pojo.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface QuestionMapper extends BaseMapper<Question> {

    @Select("select question_id from question where course_type=#{courseName} and question_type=#{questionType}")
    List<Integer> selectByCourseAndType(String courseName, String questionType);

    @Select("select * from question where course_type=#{courseName} and question_type=#{questionType}")
    List<Question> selectAllByCourseAndType(String courseName, String questionType);
}
