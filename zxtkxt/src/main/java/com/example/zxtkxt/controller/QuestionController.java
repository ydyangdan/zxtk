package com.example.zxtkxt.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.zxtkxt.core.api.ApiRest;
import com.example.zxtkxt.core.api.controller.BaseController;
import com.example.zxtkxt.mapper.QuestionMapper;
import com.example.zxtkxt.pojo.Course;
import com.example.zxtkxt.pojo.Question;
import com.example.zxtkxt.pojo.QuestionSearch;
import com.example.zxtkxt.pojo.User;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/question")
public class QuestionController extends BaseController {
    @Autowired
    private QuestionMapper questionMapper;

    /**
     * 试题分页列表查询
     *
     * @param pageNum
     * @param pageSize
     * @param courseType
     * @return
     */
    @GetMapping("/queryQuestion")
    public ApiRest<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize,
                               @RequestParam(defaultValue = "") String questionSubject,
                               @RequestParam(defaultValue = "") String questionType,
                               @RequestParam(defaultValue = "") String bankName,
                               @RequestParam(defaultValue = "") String questionLevel,
                               @RequestParam(defaultValue = "") String courseType) {
        LambdaQueryWrapper<Question> wrapper = Wrappers.<Question>lambdaQuery().orderByAsc(Question::getQuestionId);
        //按试题内容查询
        if (StrUtil.isNotBlank(questionSubject)) {
            wrapper.like(Question::getQuestionSubject, questionSubject);
        }
        //按试题类型查询
        if (StrUtil.isNotBlank(questionType)) {
            wrapper.like(Question::getQuestionType, questionType);
        }
        //按题库查询
        if (StrUtil.isNotBlank(bankName)) {
            wrapper.like(Question::getBankName, bankName);
        }
        //按难度水平查询
        if (StrUtil.isNotBlank(questionLevel)) {
            wrapper.like(Question::getQuestionLevel, questionLevel);
        }
        //按课程查询
        if (StrUtil.isNotBlank(courseType)) {
            wrapper.like(Question::getCourseType, courseType);
        }
        //分页
        Page<Question> userPage = questionMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return success(userPage);
    }

    /**
     * 删除试题
     * @return
     */
    @ApiOperation(value = "删除用户")
    @DeleteMapping("/{id}")
    public ApiRest<String> delete(@PathVariable Long id) {
        try {
            questionMapper.deleteById(id);
        }catch (Exception e){
            return failure("删除失败");
        }
        return success("删除成功");
    }

    /**
     * 编辑试题
     * @return
     */
    @ApiOperation(value = "编辑用户")
    @PutMapping()
    public ApiRest<String> update(@RequestBody Question question){

        try {
            questionMapper.updateById(question);
        }catch (Exception e){
            return failure("编辑失败");
        }
        return success("编辑成功");

    }

    /**
     * 新增试题
     * @return
     */
    @ApiOperation(value = "新增用户")
    @RequestMapping("/save")
    public ApiRest<String> save(@RequestBody Question question ){
        question.setCreateTime(new Date());
        try {
            questionMapper.insert(question);
        }catch (Exception e){
            return failure("新增失败");
        }
        return success("新增成功");

    }

    /**
     * 获取试题编号
     * @return
     */
    @ApiOperation(value = "获取试题编号")
    @RequestMapping("/getQuestions")
    public ApiRest<List<Question>> getQuestions(@RequestParam String str) {
        String[] arr = str.split(",");

        List<Integer> idList = Arrays.stream(arr).map(String::trim).map(Integer::valueOf).collect(Collectors.toList());

        List<Question> questionList;
        try {
            questionList = questionMapper.selectBatchIds(idList);
            for (Question question : questionList) {
                System.out.println(question.toString());
            }
            System.out.println(questionList.toString());
        } catch (Exception e) {
            return failure("获取数据失败");
        }
        return success("获取成功", questionList);
    }

}
