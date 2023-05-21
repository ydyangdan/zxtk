package com.example.zxtkxt.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.zxtkxt.core.api.ApiRest;
import com.example.zxtkxt.core.api.controller.BaseController;
import com.example.zxtkxt.mapper.LogsMapper;
import com.example.zxtkxt.mapper.PaperMapper;
import com.example.zxtkxt.mapper.PaperTemplateMapper;
import com.example.zxtkxt.pojo.Logs;
import com.example.zxtkxt.pojo.Paper;
import com.example.zxtkxt.pojo.PaperTemplate;
import com.example.zxtkxt.service.Ant;
import com.example.zxtkxt.service.AutoPaperService;
import com.example.zxtkxt.service.ExamPaperService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("/paper")
public class PaperController extends BaseController {
    @Autowired
    private LogsMapper logsMapper;

    @Autowired
    private PaperMapper paperMapper;

    @Autowired
    private PaperTemplateMapper paperTemplateMapper;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ExamPaperService examPaperService;

    @Autowired
    private  AutoPaperService autoPaperService;

    /**
     * 分页查询试卷
     * @param pageNum
     * @param pageSize
     * @param paperName
     * @param courseType
     * @return
     */
    @ApiOperation(value = "查询试卷")
    @GetMapping("/queryPaper")
    public ApiRest<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer pageSize,
                               @RequestParam(defaultValue = "" ) String paperName,
                               @RequestParam(defaultValue = "") String courseType) {
        LambdaQueryWrapper<Paper> wrapper = Wrappers.<Paper>lambdaQuery().orderByAsc(Paper::getPaperId);
        //按试卷名称查询
        if (StrUtil.isNotBlank(paperName)) {
            wrapper.like(Paper::getPaperName, paperName);
        }
        if (StrUtil.isNotBlank(courseType)) {
            wrapper.like(Paper::getCourseName, courseType);
        }
        String userName = (String) request.getSession().getAttribute("userName");
        wrapper.eq(Paper::getPaperAuthor, userName);
        Page<Paper> userPage = paperMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return success(userPage);
    }

    /**
     * 删除试卷
     * @return
     */
    @ApiOperation(value = "删除试卷")
    @DeleteMapping("/{id}")
    public ApiRest<String> delete(@PathVariable Long id) {
        try {
            paperMapper.deleteById(id);
        }catch (Exception e){
            return failure("删除失败");
        }
        return success("删除成功");
    }

    /**
     * 编辑试卷
     * @return
     */
    @ApiOperation(value = "编辑试卷")
    @PutMapping()
    public ApiRest<String> update(@RequestBody Paper paper){

        try {
            paperMapper.updateById(paper);
        }catch (Exception e){
            return failure("编辑失败");
        }
        return success("编辑成功");

    }

    /**
     * 从摸板新增试卷
     * @return
     */
    @ApiOperation(value = "新增试卷")
    @PostMapping("/addPaper")
    public ApiRest<String> add(@RequestBody String data){
        // 获取程序开始时间
        long startTime = System.currentTimeMillis();
        Logs logs = new Logs();

        JSONObject jsonObj = JSONObject.parseObject(data);
        String courseName = jsonObj.getString("courseName");
        String paperName = jsonObj.getString("paperName");
        String autoType = jsonObj.getString("autoType");
        String templateId = jsonObj.getString("templateId");
        PaperTemplate paperTemplate;
        try {
           paperTemplate = paperTemplateMapper.selectById(templateId);
        }catch (Exception e){
            return failure("编号错误");
        }
        paperTemplate.setPaperName(paperName);
        paperTemplate.setCourseName(courseName);
        paperTemplate.setAutoType(autoType);
        String userName = (String) request.getSession().getAttribute("userName");
        Paper paper;
        if("蚁群算法".equals(paperTemplate.getAutoType())){
            paper = autoPaperService.createPaper(paperTemplate);
            paper.setPaperAuthor(userName);
            paper.setCreateTime(new Date());
            paper.setAutoType(paperTemplate.getAutoType());
            paper.setChoiceScore(paperTemplate.getChoiceScore());
            paper.setMultiScore(paperTemplate.getMultiScore());
            paper.setBlankFillingScore(paperTemplate.getBlankFillingScore());
            paper.setJudgeScore(paperTemplate.getJudgeScore());
            paper.setShortScore(paperTemplate.getShortScore());
            try {
                paperMapper.insert(paper);
            }catch (Exception e){
                return failure("创建失败");
            }
        }else {
            paper = examPaperService.createPaper(paperTemplate);
            paper.setPaperAuthor(userName);
            paper.setCreateTime(new Date());
            paper.setAutoType(paperTemplate.getAutoType());
            try {
                paperMapper.insert(paper);
            }catch (Exception e){
                return failure("创建失败");
            }
        }
        // 获取程序结束时间
        long endTime = System.currentTimeMillis();
        // 将毫秒转换为秒
        double executionTimeSeconds = (endTime - startTime) / 1000.0;
        logs.setUserName(userName);
        logs.setPaperName(paperName);
        logs.setAutoType(paperTemplate.getAutoType());
        logs.setPaperDuration(executionTimeSeconds);
        logs.setOperateTime(new Date());
        logs.setDifficulty(paper.getPaperLevel());
        logs.setPointCoverage(paper.getPointCoverage());
        logs.setRepeatability(paper.getRepeatability());
        logs.setTargetDifficulty(paperTemplate.getDifficulty());
        logs.setTargetPointCoverage(paperTemplate.getPointCoverage());
        logs.setTargetRepeatability(paperTemplate.getRepeatability());

        //适应度
        double fitness = (Math.abs(new Ant().getDifficultyWeight() * (paper.getPaperLevel()-paperTemplate.getDifficulty()))+
                Math.abs(new Ant().getRepeatabilityWeight()  *(paper.getRepeatability()-paperTemplate.getRepeatability()))+
                Math.abs(new Ant().getPointCoverageWeight() * (paper.getPointCoverage()-paperTemplate.getPointCoverage())))/
                (new Ant().getDifficultyWeight()+new Ant().getRepeatabilityWeight()+new Ant().getPointCoverageWeight());
        System.out.println("log适应度"+fitness);
        logs.setFitness(fitness);
        logsMapper.insert(logs);
        return success("创建成功","创建成功");
    }

    /**
     * 创建试卷
     * @return
     */
    @ApiOperation(value = "创建试卷")
    @RequestMapping("/createPaper")
    public ApiRest<String> createPaper(@RequestBody PaperTemplate paperTemplate )  {
        // 获取程序开始时间
        long startTime = System.currentTimeMillis();
        Logs logs = new Logs();

        String userName = (String) request.getSession().getAttribute("userName");
        Paper paper;
        if("蚁群算法".equals(paperTemplate.getAutoType())){
             paper = autoPaperService.createPaper(paperTemplate);
            paper.setPaperAuthor(userName);
            paper.setCreateTime(new Date());
            paper.setAutoType(paperTemplate.getAutoType());
            paper.setChoiceScore(paperTemplate.getChoiceScore());
            paper.setMultiScore(paperTemplate.getMultiScore());
            paper.setBlankFillingScore(paperTemplate.getBlankFillingScore());
            paper.setJudgeScore(paperTemplate.getJudgeScore());
            paper.setShortScore(paperTemplate.getShortScore());
            System.out.println("蚁群算法组卷"+paper);
            try {
                paperMapper.insert(paper);

            }catch (Exception e){
                return failure("创建失败");
            }
        }else {
             paper = examPaperService.createPaper(paperTemplate);
             paper.setPaperAuthor(userName);
             paper.setCreateTime(new Date());
             paper.setAutoType(paperTemplate.getAutoType());
            try {
                paperMapper.insert(paper);

            }catch (Exception e){
                return failure("创建失败");
            }
        }
        // 获取程序结束时间
        long endTime = System.currentTimeMillis();
        // 将毫秒转换为秒
        double executionTimeSeconds = (endTime - startTime) / 1000.0;
        logs.setUserName(userName);
        logs.setPaperName(paperTemplate.getPaperName());
        logs.setAutoType(paperTemplate.getAutoType());
        logs.setPaperDuration(executionTimeSeconds);
        logs.setOperateTime(new Date());
        logs.setDifficulty(paper.getPaperLevel());
        logs.setPointCoverage(paper.getPointCoverage());
        logs.setRepeatability(paper.getRepeatability());
        logs.setTargetDifficulty(paperTemplate.getDifficulty());
        logs.setTargetPointCoverage(paperTemplate.getPointCoverage());
        logs.setTargetRepeatability(paperTemplate.getRepeatability());

        //适应度
        double fitness = (Math.abs(new Ant().getDifficultyWeight() * (paper.getPaperLevel()-paperTemplate.getDifficulty()))+
                Math.abs(new Ant().getRepeatabilityWeight()  *(paper.getRepeatability()-paperTemplate.getRepeatability()))+
                Math.abs(new Ant().getPointCoverageWeight() * (paper.getPointCoverage()-paperTemplate.getPointCoverage())))/
                (new Ant().getDifficultyWeight()+new Ant().getRepeatabilityWeight()+new Ant().getPointCoverageWeight());
        System.out.println("log适应度"+fitness);
        logs.setFitness(fitness);
        logsMapper.insert(logs);
        return success("创建成功","创建成功");

    }

    /**
     * 手动组卷
     * @return
     */
    @ApiOperation(value = "手动组卷")
    @PostMapping("/save")
    public ApiRest<String> save(@RequestBody Paper paper){
        String userName = (String) request.getSession().getAttribute("userName");
        paper.setPaperAuthor(userName);
        paper.setCreateTime(new Date());
        paper.setAutoType("手动组卷");
        try {
            paperMapper.insert(paper);

        }catch (Exception e){
            return failure("创建失败");
        }
        return success("创建成功","创建成功");

    }

    /**
     * 测试
     * @return
     */
    @ApiOperation(value = "测试")
    @RequestMapping("/test")
    public String test(){
        // 获取程序开始时间
        long startTime = System.currentTimeMillis();
        Logs logs = new Logs();

        PaperTemplate paperTemplate;
        try {
            paperTemplate = paperTemplateMapper.selectById(5);
        }catch (Exception e){
            return "编号错误";
        }
        paperTemplate.setPaperName("测试算法");
        paperTemplate.setCourseName("java基础");
        paperTemplate.setAutoType("蚁群算法");
        String userName = (String) request.getSession().getAttribute("userName");
        Paper paper = autoPaperService.createPaper(paperTemplate);
        paper.setPaperAuthor(userName);
        paper.setCreateTime(new Date());
        paper.setAutoType(paperTemplate.getAutoType());
        paper.setChoiceScore(paperTemplate.getChoiceScore());
        paper.setMultiScore(paperTemplate.getMultiScore());
        paper.setBlankFillingScore(paperTemplate.getBlankFillingScore());
        paper.setJudgeScore(paperTemplate.getJudgeScore());
        paper.setShortScore(paperTemplate.getShortScore());
        System.out.println("组卷结果："+paper.toString());
        // 获取程序结束时间
        long endTime = System.currentTimeMillis();
        // 将毫秒转换为秒
        double executionTimeSeconds = (endTime - startTime) / 1000.0;
        logs.setUserName(userName);
        logs.setPaperName("测试算法");
        logs.setAutoType(paperTemplate.getAutoType());
        logs.setPaperDuration(executionTimeSeconds);
        logs.setOperateTime(new Date());
        logs.setDifficulty(paper.getPaperLevel());
        logs.setPointCoverage(paper.getPointCoverage());
        logs.setRepeatability(paper.getRepeatability());
        logs.setTargetDifficulty(paperTemplate.getDifficulty());
        logs.setTargetPointCoverage(paperTemplate.getPointCoverage());
        logs.setTargetRepeatability(paperTemplate.getRepeatability());

        //适应度
        double fitness = (Math.abs(new Ant().getDifficultyWeight() * (paper.getPaperLevel()-paperTemplate.getDifficulty()))+
                Math.abs(new Ant().getRepeatabilityWeight()  *(paper.getRepeatability()-paperTemplate.getRepeatability()))+
                Math.abs(new Ant().getPointCoverageWeight() * (paper.getPointCoverage()-paperTemplate.getPointCoverage())))/
                (new Ant().getDifficultyWeight()+new Ant().getRepeatabilityWeight()+new Ant().getPointCoverageWeight());
        logs.setFitness(fitness);
        logsMapper.insert(logs);
        return paper.toString();
    }



}
