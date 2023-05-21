package com.example.zxtkxt.service;

import com.example.zxtkxt.mapper.QuestionMapper;
import com.example.zxtkxt.pojo.Paper;
import com.example.zxtkxt.pojo.PaperTemplate;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Data
public class ExamPaperService {

    @Autowired
    private QuestionMapper questionMapper;

    public Paper createPaper(PaperTemplate paperTemplate) {
        Paper paper = randomPaper(paperTemplate);
        return paper;
    }

    //随机生成试卷
    private Paper  randomPaper(PaperTemplate paperTemplate){

        Paper paper = new Paper();

        paper.setPaperName(paperTemplate.getPaperName());
        paper.setCourseName(paperTemplate.getCourseName());
        paper.setCreateTime(new Date());

       //从数据库查询对应课程单选题的试题id
        List<Integer> choiceList = questionMapper.selectByCourseAndType(paperTemplate.getCourseName(), "单选题");
        //随机选取指定数量的试题
        // Select a random subset of questions
        List<Integer> selectedChoiceList= new ArrayList<>();
        Random random = new Random();
        while (selectedChoiceList.size() < paperTemplate.getChoiceCount() && !choiceList.isEmpty()) {
            int index = random.nextInt(choiceList.size());
            Integer id = choiceList.remove(index);
            selectedChoiceList.add(id);
        }
         paper.setChoiceQuestion(selectedChoiceList.stream().map(Object::toString).collect(Collectors.joining(",")));
         paper.setChoiceScore(paperTemplate.getChoiceScore());

        //从数据库查询对应课程多选题的试题id
        List<Integer> multiList = questionMapper.selectByCourseAndType(paperTemplate.getCourseName(), "多选题");
        //随机选取指定数量的试题
        // Select a random subset of questions
        List<Integer> selectedMultiListList= new ArrayList<>();
        while (selectedMultiListList.size() < paperTemplate.getMultiCount() && !multiList.isEmpty()) {
            int index = random.nextInt(multiList.size());
            Integer id = multiList.remove(index);
            selectedMultiListList.add(id);
        }
        paper.setMultiQuestion(selectedMultiListList.stream().map(Object::toString).collect(Collectors.joining(",")));
        paper.setMultiScore(paperTemplate.getMultiScore());

        //从数据库查询对应课程对应题型的试题id
        List<Integer>  blankFillingCountList= questionMapper.selectByCourseAndType(paperTemplate.getCourseName(), "填空题");
        //随机选取指定数量的试题
        // Select a random subset of questions
        List<Integer> selectedBlankFillingCountList= new ArrayList<>();

        while (selectedBlankFillingCountList.size() < paperTemplate.getBlankFillingCount() && !blankFillingCountList.isEmpty()) {
            int index = random.nextInt(blankFillingCountList.size());
            Integer id = blankFillingCountList.remove(index);
            selectedBlankFillingCountList.add(id);
        }
        paper.setBlankFillingQuestion(selectedBlankFillingCountList.stream().map(Object::toString).collect(Collectors.joining(",")));
        paper.setBlankFillingScore(paperTemplate.getBlankFillingScore());


        //从数据库查询对应课程对应题型的试题id
        List<Integer> judgeList = questionMapper.selectByCourseAndType(paperTemplate.getCourseName(), "判断题");
        //随机选取指定数量的试题
        // Select a random subset of questions
        List<Integer> selectedJudgeList= new ArrayList<>();
        while (selectedJudgeList.size() < paperTemplate.getJudgeCount() && !judgeList.isEmpty()) {
            int index = random.nextInt(judgeList.size());
            Integer id = judgeList.remove(index);
            selectedJudgeList.add(id);
        }
        paper.setJudgeQuestion(selectedJudgeList.stream().map(Object::toString).collect(Collectors.joining(",")));
        paper.setJudgeScore(paperTemplate.getJudgeScore());

        //从数据库查询对应课程对应题型的试题id
        List<Integer> shortList = questionMapper.selectByCourseAndType(paperTemplate.getCourseName(), "简答题");
        //随机选取指定数量的试题
        // Select a random subset of questions
        List<Integer> selectedShortList= new ArrayList<>();
        while (selectedShortList.size() < paperTemplate.getShortCount() && !shortList.isEmpty()) {
            int index = random.nextInt(shortList.size());
            Integer id = shortList.remove(index);
            selectedShortList.add(id);
        }
        paper.setShortQuestion(selectedShortList.stream().map(Object::toString).collect(Collectors.joining(",")));
        paper.setShortScore(paperTemplate.getShortScore());

        System.out.println(paper.toString());

        paper.setPaperLevel(paperTemplate.getDifficulty());
        paper.setRepeatability(paperTemplate.getRepeatability());
        paper.setPointCoverage(paperTemplate.getPointCoverage());

        return paper;
    }

}
