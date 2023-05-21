package com.example.zxtkxt.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.zxtkxt.core.api.ApiRest;
import com.example.zxtkxt.core.api.controller.BaseController;
import com.example.zxtkxt.mapper.KnowledgePointMapper;
import com.example.zxtkxt.pojo.Course;
import com.example.zxtkxt.pojo.KnowledgePoint;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/point")
public class KnowledgePointController extends BaseController {
    @Autowired
    private KnowledgePointMapper knowledgePointMapper;
    @Autowired
    private HttpServletRequest request;

    /**
     * 知识点分页列表查询
     *
     * @param pageNum
     * @param pageSize
     * @param pointName
     * @return
     */
    @GetMapping("/queryPoint")
    public ApiRest<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize,
                               @RequestParam(defaultValue = "" ) String pointName,
                               @RequestParam(defaultValue = "" ) String courseName) {
        LambdaQueryWrapper<KnowledgePoint> wrapper = Wrappers.<KnowledgePoint>lambdaQuery().orderByAsc(KnowledgePoint::getPointId);
        if (StrUtil.isNotBlank(pointName)) {
            wrapper.like(KnowledgePoint::getPointName, pointName);
        }
        if (StrUtil.isNotBlank(courseName)) {
            wrapper.like(KnowledgePoint::getCourseName, courseName);
        }
        Page<KnowledgePoint> userPage = knowledgePointMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return success(userPage);
    }

    /**
     * 删除知识点
     * @return
     */
    @ApiOperation(value = "删除知识点")
    @DeleteMapping("/{id}")
    public ApiRest<String> delete(@PathVariable Long id) {
        try {
            knowledgePointMapper.deleteById(id);
        }catch (Exception e){
            return failure("删除失败");
        }
        return success("删除成功");
    }

    /**
     * 编辑知识点
     * @return
     */
    @ApiOperation(value = "编辑知识点")
    @PutMapping()
    public ApiRest<String> update(@RequestBody KnowledgePoint knowledgePoint){

        try {
            knowledgePointMapper.updateById(knowledgePoint);
        }catch (Exception e){
            return failure("编辑失败");
        }
        return success("编辑成功");

    }

    /**
     * 新增知识点
     * @return
     */
    @ApiOperation(value = "新增知识点")
    @RequestMapping("/save")
    public ApiRest<String> save(@RequestBody KnowledgePoint knowledgePoint){
        String userName =(String) request.getSession().getAttribute("userName");
        knowledgePoint.setUserName(userName);
        knowledgePoint.setCreateTime(new Date());
        try {
            knowledgePointMapper.insert(knowledgePoint);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return failure("新增失败");
        }
        return success("新增成功");

    }

    /**
     * 课程对应的知识点查询
     *
     * @return
     */
    @ApiOperation(value = "查询知识点名称")
    @GetMapping("/getPointName")
    public ApiRest<?> getPointName(@RequestParam String courseType) {
        System.out.println(courseType+"1111111111111111111111111111111111111111");
        List<String> strings = knowledgePointMapper.selectPointName(courseType);
        for (String s : strings) {
            System.out.println(s);
        }
        System.out.println(strings.toString());
        return success(strings);
    }

}