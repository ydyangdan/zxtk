package com.example.zxtkxt.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.zxtkxt.core.api.ApiRest;
import com.example.zxtkxt.core.api.controller.BaseController;
import com.example.zxtkxt.mapper.CourseMapper;
import com.example.zxtkxt.pojo.Course;
import com.example.zxtkxt.pojo.Paper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RequestMapping("/course")
@RestController
public class CourseController extends BaseController {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private HttpServletRequest request;

    /**
     * 课程分页列表查询
     *
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @ApiOperation(value = "查询课程")
    @GetMapping("/queryCourse")
    public ApiRest<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize,
                               @RequestParam(defaultValue = "" ) String search) {
        LambdaQueryWrapper<Course> wrapper = Wrappers.<Course>lambdaQuery().orderByAsc(Course::getCourseId);
        String userName =(String) request.getSession().getAttribute("userName");

        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Course::getCourseName, search);
        }
        wrapper.eq(Course::getCourseTeacher, "公共").or().eq(Course::getCourseTeacher,userName);

        Page<Course> userPage = courseMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return success(userPage);
    }

    /**
     * 新增课程
     * @return
     */
    @ApiOperation(value = "新增课程")
    @RequestMapping("/save")
    public ApiRest<String> save(@RequestBody Course course ){
        course.setCreateTime(new Date());
        String userName = (String) request.getSession().getAttribute("userName");
        course.setCourseTeacher(userName);
        try {
            courseMapper.insert(course);
        }catch (Exception e){
            return failure("新增失败");
        }
        return success("新增成功");

    }

    /**
     * 删除课程
     * @return
     */
    @ApiOperation(value = "删除课程")
    @DeleteMapping("/{id}")
    public ApiRest<String> delete(@PathVariable Long id) {
        try {
            courseMapper.deleteById(id);
        }catch (Exception e){
            return failure("删除失败");
        }
        return success("删除成功");
    }

    /**
     * 编辑课程
     * @return
     */
    @ApiOperation(value = "编辑课程")
    @PutMapping()
    public ApiRest<String> update(@RequestBody Course course){

        try {
            courseMapper.updateById(course);
        }catch (Exception e){
            return failure("编辑失败");
        }
        return success("编辑成功");

    }

    /**
     * 该用户创建的课程名查询
     *
     * @return
     */
    @ApiOperation(value = "查询课程名")
    @GetMapping("/getCourseName")
    public ApiRest<?> getCourseName() {
        LambdaQueryWrapper<Course> wrapper = Wrappers.<Course>lambdaQuery().orderByAsc(Course::getCourseId);
        String userName =(String) request.getSession().getAttribute("userName");
        List<String> strings = courseMapper.selectCourseName(userName);
        for (String s : strings) {
            System.out.println(s);
        }
        System.out.println(strings.toString());
        return success(strings);
    }

}
