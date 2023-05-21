package com.example.zxtkxt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.zxtkxt.pojo.Course;
import com.example.zxtkxt.pojo.QuestionBank;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionBankMapper extends BaseMapper<QuestionBank> {


    @Select("select bank_name from  question_bank where user_name=#{userName}")
    List<String> selectBankName(String userName);
}
