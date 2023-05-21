package com.example.zxtkxt.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.zxtkxt.core.api.ApiRest;
import com.example.zxtkxt.core.api.controller.BaseController;
import com.example.zxtkxt.mapper.PaperTemplateMapper;
import com.example.zxtkxt.pojo.Course;
import com.example.zxtkxt.pojo.PaperTemplate;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.HandshakeCompletedEvent;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("/template")
public class PaperTemplateController extends BaseController {
    @Autowired
    private PaperTemplateMapper paperTemplateMapper;

    @Autowired
    private HttpServletRequest request;

    /**
     * 模板分页列表查询
     *
     * @param pageNum
     * @param pageSize
     * @param search
     * @return
     */
    @ApiOperation(value = "查询模板")
    @GetMapping("/queryTemplate")
    public ApiRest<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize,
                               @RequestParam(defaultValue = "" ) String search) {
        LambdaQueryWrapper<PaperTemplate> wrapper = Wrappers.<PaperTemplate>lambdaQuery().orderByAsc(PaperTemplate::getTemplateId);
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(PaperTemplate::getCourseName, search);
        }
        String userName = (String) request.getSession().getAttribute("userName");
        wrapper.eq(PaperTemplate::getUserName,userName).or().eq(PaperTemplate::getUserName, "公共");
        Page<PaperTemplate> userPage = paperTemplateMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return success(userPage);
    }

    /**
     * 删除模板
     * @return
     */
    @ApiOperation(value = "删除模板")
    @DeleteMapping("/{id}")
    public ApiRest<String> delete(@PathVariable Long id) {
        try {
            paperTemplateMapper.deleteById(id);
        }catch (Exception e){
            return failure("删除失败");
        }
        return success("删除成功");
    }

    /**
     * 编辑模板
     * @return
     */
    @ApiOperation(value = "编辑模板")
    @PutMapping()
    public ApiRest<String> update(@RequestBody PaperTemplate paperTemplate){

        try {
            paperTemplateMapper.updateById(paperTemplate);
        }catch (Exception e){
            return failure("编辑失败");
        }
        return success("编辑成功");

    }

    /**
     * 新增模板
     * @return
     */
    @ApiOperation(value = "新增模板")
    @RequestMapping("/save")
    public ApiRest<String> save(@RequestBody PaperTemplate paperTemplate ){
        String userName = (String) request.getSession().getAttribute("userName");
        paperTemplate.setUserName(userName);
        paperTemplate.setCreateTime(new Date());
        try {
            paperTemplateMapper.insert(paperTemplate);
        }catch (Exception e){
            return failure("新增失败");
        }
        return success("新增成功");

    }
}
