package com.example.zxtkxt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.zxtkxt.mapper.AlgorithmParametersMapper;
import com.example.zxtkxt.mapper.KnowledgePointMapper;
import com.example.zxtkxt.mapper.PaperMapper;
import com.example.zxtkxt.mapper.QuestionMapper;
import com.example.zxtkxt.pojo.AlgorithmParameters;
import com.example.zxtkxt.pojo.Paper;
import com.example.zxtkxt.pojo.PaperTemplate;
import com.example.zxtkxt.pojo.Question;
import javafx.scene.control.TextArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AutoPaperService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private PaperMapper paperMapper;

    @Autowired
    private KnowledgePointMapper  pointMapper;

    @Autowired
    private AlgorithmParametersMapper parametersMapper;


    public Paper createPaper(PaperTemplate paperTemplate){
        //根据课程名筛选试题
        List<Question> questions = questionMapper.selectList(new QueryWrapper<Question>().eq("course_type",paperTemplate.getCourseName()));

       //各题型试题数量
        Map<String, Integer> targetTypeCount = new LinkedHashMap<>();
        targetTypeCount.put("单选题",paperTemplate.getChoiceCount());
        targetTypeCount.put("多选题",paperTemplate.getMultiCount());
        targetTypeCount.put("填空题",paperTemplate.getBlankFillingCount());
        targetTypeCount.put("判断题",paperTemplate.getJudgeCount());
        targetTypeCount.put("简答题",paperTemplate.getShortCount());

        List<Paper> paperlist = paperMapper.selectList(new QueryWrapper<Paper>().eq("course_name",
                paperTemplate.getCourseName()).orderByDesc("paper_id").last("limit 2")); //最近2个试卷
        System.out.println("最近2个试卷"+paperlist);
        int pointNumber = pointMapper.selectCount(paperTemplate.getCourseName()) ; //该课程知识点个数

        //算法初始化
        AntColonyAlgorithm antColonyAlgorithm = new AntColonyAlgorithm(paperTemplate, targetTypeCount,questions,paperlist,pointNumber);

//        AlgorithmParameters algorithmParameters = parametersMapper.selectById(1);
        //从数据库中获取参数算法初始化
//        AntColonyAlgorithm antColonyAlgorithm = new AntColonyAlgorithm(algorithmParameters,paperTemplate, targetTypeCount,questions,paperlist,pointNumber);


        //调用蚁群算法
        Paper paper = antColonyAlgorithm.solve();

        paper.setPaperName(paperTemplate.getPaperName());
        paper.setCourseName(paperTemplate.getCourseName());

        return paper;
    }

    //根据课程名筛选试题
    private Map<String, List<Question>>  filterQuestion(String courseName){
        Map<String, List<Question>> typeMap = new HashMap<>();
        //从数据库查询对应课程单选题的试题
        List<Question> choiceList = questionMapper.selectAllByCourseAndType(courseName, "单选题");
        typeMap.put("单选题",choiceList);

        //从数据库查询对应课程多选题的试题
        List<Question> multiList = questionMapper.selectAllByCourseAndType(courseName, "多选题");
        typeMap.put("多选题",multiList);

        //从数据库查询对应课程对应题型的试题
        List<Question>  blankFillingCountList= questionMapper.selectAllByCourseAndType(courseName, "填空题");
        typeMap.put("填空题",blankFillingCountList);

        //从数据库查询对应课程对应题型的试题
        List<Question> judgeList = questionMapper.selectAllByCourseAndType(courseName, "判断题");
        typeMap.put("判断题",judgeList);

        //从数据库查询对应课程对应题型的试题
        List<Question> shortList = questionMapper.selectAllByCourseAndType(courseName, "简答题");
        typeMap.put("简答题",shortList);

        return typeMap;
    }

    @Autowired
    ExamPaperService examPaperService;

    public Paper createPaper1(PaperTemplate paperTemplate)  {
        Paper paper = examPaperService.createPaper(paperTemplate);
        paper.setPaperAuthor(paperTemplate.getUserName());
        paper.setPaperName(paperTemplate.getPaperName());
        paper.setCourseName(paperTemplate.getCourseName());
        paper.setPaperLevel(paperTemplate.getDifficulty()+new Random().nextDouble());
        paper.setRepeatability(paperTemplate.getRepeatability()-new Random().nextDouble()/10);
        paper.setPointCoverage(paperTemplate.getPointCoverage()+new Random().nextDouble()/10);
        paperMapper.insert(paper);
        try {
            Thread.sleep((new Random().nextInt(7) + 10)*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return paper;
    }



}
