package com.example.zxtkxt.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.zxtkxt.core.api.ApiRest;
import com.example.zxtkxt.core.api.controller.BaseController;
import com.example.zxtkxt.mapper.QuestionBankMapper;
import com.example.zxtkxt.pojo.Course;
import com.example.zxtkxt.pojo.Question;
import com.example.zxtkxt.pojo.QuestionBank;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/bank")
public class QuestionBankController extends BaseController {

    @Autowired
    private QuestionBankMapper questionBankMapper;

    @Autowired
    private HttpServletRequest request;

    /**
     * 题库分页列表查询
     *
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @GetMapping("/queryQuestionBank")
    public ApiRest<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize,
                               @RequestParam(defaultValue = "" ) String search) {
        LambdaQueryWrapper<QuestionBank> wrapper = Wrappers.<QuestionBank>lambdaQuery().orderByAsc(QuestionBank::getBankId);
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(QuestionBank::getBankName, search);
        }

        Page<QuestionBank> userPage = questionBankMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return success(userPage);
    }

    /**
     * 删除题库
     * @return
     */
    @ApiOperation(value = "删除用户")
    @DeleteMapping("/{id}")
    public ApiRest<String> delete(@PathVariable Long id) {
        try {
            questionBankMapper.deleteById(id);
        }catch (Exception e){
            return failure("删除失败");
        }
        return success("删除成功");
    }

    /**
     * 编辑题库
     * @return
     */
    @ApiOperation(value = "编辑用户")
    @PutMapping()
    public ApiRest<String> update(@RequestBody QuestionBank questionBank){

        try {
            questionBankMapper.updateById(questionBank);
        }catch (Exception e){
            return failure("编辑失败");
        }
        return success("编辑成功");

    }

    /**
     * 新增题库
     * @return
     */
    @ApiOperation(value = "新增题库")
    @RequestMapping("/save")
    public ApiRest<String> save(@RequestBody QuestionBank questionBank ){
        questionBank.setCreateTime(new Date());
        try {
            questionBankMapper.insert(questionBank);
        }catch (Exception e){
            return failure("新增失败");
        }
        return success("新增成功");

    }

    /**
     * 该用户创建的题库名查询
     *
     * @return
     */
    @ApiOperation(value = "查询题库名称")
    @GetMapping("/getBankName")
    public ApiRest<?> getBankName() {
        LambdaQueryWrapper<QuestionBank> wrapper = Wrappers.<QuestionBank>lambdaQuery().orderByAsc(QuestionBank::getBankId);
        String userName =(String) request.getSession().getAttribute("userName");
        List<String> strings = questionBankMapper.selectBankName(userName);
        for (String s : strings) {
            System.out.println(s);
        }
        System.out.println(strings.toString());
        return success(strings);
    }

}
