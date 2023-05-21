package com.example.zxtkxt.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.zxtkxt.core.api.ApiRest;
import com.example.zxtkxt.core.api.controller.BaseController;
import com.example.zxtkxt.mapper.LogsMapper;
import com.example.zxtkxt.mapper.PaperMapper;
import com.example.zxtkxt.pojo.Logs;
import com.example.zxtkxt.pojo.Paper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/logs")
public class LogsController extends BaseController {

    @Autowired
    private LogsMapper logsMapper;

    @GetMapping("/queryLogs")
    public ApiRest<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<Logs> wrapper = Wrappers.<Logs>lambdaQuery().orderByAsc(Logs::getId);

        Page<Logs> userPage = logsMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return success(userPage);
    }
}
