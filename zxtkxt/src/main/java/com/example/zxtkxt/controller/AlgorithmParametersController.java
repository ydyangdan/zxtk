package com.example.zxtkxt.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.zxtkxt.core.api.ApiRest;
import com.example.zxtkxt.core.api.controller.BaseController;
import com.example.zxtkxt.mapper.AlgorithmParametersMapper;
import com.example.zxtkxt.mapper.LogsMapper;
import com.example.zxtkxt.pojo.AlgorithmParameters;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/parameters")
public class AlgorithmParametersController extends BaseController {
    @Autowired
    private AlgorithmParametersMapper parametersMapper;

    @GetMapping("/queryParameters")
    public ApiRest<?> find() {
        AlgorithmParameters parameters = parametersMapper.selectById(1);
        System.out.println(parameters);
        return success(parameters);
    }

    /**
     * 编辑算法参数
     * @return
     */
    @ApiOperation(value = "编辑算法参数")
    @PutMapping()
    public ApiRest<String> put(@RequestBody AlgorithmParameters parameters){
        System.out.println(parameters.toString());
        parameters.setCreateTime(new Date());
        try {
            parametersMapper.updateById(parameters);
        }catch (Exception e){
            return failure("保存失败");
        }
        return success("保存成功");
    }
}
