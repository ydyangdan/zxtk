package com.example.zxtkxt.service;

import com.example.zxtkxt.algorithm.ExamPaper;
import com.example.zxtkxt.pojo.Paper;
import com.example.zxtkxt.pojo.PaperTemplate;
import com.example.zxtkxt.pojo.Question;
import lombok.Data;

import java.util.*;


@Data
public class Ant {
    private int[] selectedQuestions; // 蚂蚁已选择的试题
    
    private double fitness; // 蚂蚁所组成试卷的适应度
    private List<Question> questionList; // 所有试题列表
    
    private int numQuestions; // 所需试卷试题总数

    PaperTemplate paperTemplate;

    List<Paper> paperlist; //最近2个试卷
    int pointNumber = 13; //该课程知识点个数

    double difficultyWeight=0.7;
    double repeatabilityWeight = 0.1;
    double pointCoverageWeight = 0.2;

    public Ant(PaperTemplate paperTemplate,List<Question> questionList,List<Paper> paperlist,int pointNumber){
        this.numQuestions=paperTemplate.getChoiceCount()+paperTemplate.getMultiCount()+
                paperTemplate.getShortCount()+paperTemplate.getJudgeCount()
                +paperTemplate.getBlankFillingCount();
        this.selectedQuestions=new int[numQuestions];
        for (int i = 0; i < selectedQuestions.length; i++) {
            selectedQuestions[i]=-1;
        }
        this.paperTemplate=paperTemplate;
        this.questionList=questionList;
        this.paperlist=paperlist;
        this.pointNumber=pointNumber;
    }



    public Ant(PaperTemplate paperTemplate) {
        this.paperTemplate=paperTemplate;
    }

    public Ant() {

    }

    //获取已选择的最后一道试题
    public int getSelectedLast(){
        for (int i = 0; i < selectedQuestions.length; i++) {
            if (selectedQuestions[i]==-1){
                return i;
            }
        }
        return 0;
    }
    //加入新选择的试题
    public void add(int n){
        for (int i = 0; i < selectedQuestions.length; i++) {
            if (selectedQuestions[i]==-1){
                selectedQuestions[i]= n;
                break;
            }
        }
    }

    // 计算适应度
    public double calculateFitness() {
         //试卷难度计算
        double difficulty = this.calculateDifficulty();
        double repeatability = this.calculateRepeatability();
        double pointCoverage = this.calculatePointCoverage();

        //适应度
        fitness = (Math.abs(difficultyWeight *(difficulty-paperTemplate.getDifficulty()))+
                Math.abs(repeatabilityWeight *(repeatability-paperTemplate.getRepeatability()))+
                Math.abs(pointCoverageWeight *(pointCoverage-paperTemplate.getPointCoverage())))/
                (difficultyWeight+repeatabilityWeight+pointCoverageWeight);
//        System.out.println("适应度："+fitness);
        return fitness;
    }


    //是否满足约束条件
    public boolean isConstraint(){
       boolean bl = this.calculateRepeatability()<paperTemplate.getRepeatability()&&
               this.calculatePointCoverage()>paperTemplate.getPointCoverage();
        return bl;
    }

    //试卷难度计算
    public double calculateDifficulty(){
        //试卷难度计算
        double difficulty = 0;
        double totalScore=paperTemplate.getChoiceScore()*paperTemplate.getChoiceCount()+paperTemplate.getMultiScore()*paperTemplate.getMultiCount()+
                paperTemplate.getShortScore()*paperTemplate.getShortCount()+paperTemplate.getJudgeScore()*paperTemplate.getJudgeCount()
                +paperTemplate.getBlankFillingScore()*paperTemplate.getBlankFillingCount();
        for (int i = 0; i < selectedQuestions.length; i++) {
            if ("单选题".equals(questionList.get(selectedQuestions[i]).getQuestionType())){
                difficulty+=questionList.get(selectedQuestions[i]).getDifficulty()*paperTemplate.getChoiceScore();
            }
            if ("多选题".equals(questionList.get(selectedQuestions[i]).getQuestionType())){
                difficulty+=questionList.get(selectedQuestions[i]).getDifficulty()*paperTemplate.getMultiScore();
            }
            if ("判断题".equals(questionList.get(selectedQuestions[i]).getQuestionType())){
                difficulty+=questionList.get(selectedQuestions[i]).getDifficulty()*paperTemplate.getJudgeScore();
            }
            if ("填空题".equals(questionList.get(selectedQuestions[i]).getQuestionType())){
                difficulty+=questionList.get(selectedQuestions[i]).getDifficulty()*paperTemplate.getBlankFillingScore();
            }
            if ("简答题".equals(questionList.get(selectedQuestions[i]).getQuestionType())){
                difficulty+=questionList.get(selectedQuestions[i]).getDifficulty()*paperTemplate.getShortScore();
            }
        }
        //加权平均
        difficulty=difficulty/totalScore;
        return difficulty;
    }

    //试卷相似度计算
    public double calculateRepeatability(){
        Paper paper = paperlist.get(0);
        Paper paper1 = paperlist.get(1);
        int[] ints = change(paper);
        int[] ints1 = change(paper1);
        int [] selected = new int[selectedQuestions.length];

        for (int i = 0; i < selectedQuestions.length; i++) {
            selected[i]=questionList.get(selectedQuestions[i]).getQuestionId();
        }
        double repeatability = calculateCommonElementsRatio(selectedQuestions, ints, ints1);
        return repeatability;
    }

    //试卷知识点覆盖度计算
    public double calculatePointCoverage(){
        List<String> point = new LinkedList();
        for (int i = 0; i < selectedQuestions.length; i++) {
            String questionPoint = questionList.get(selectedQuestions[i]).getQuestionPoint();
            if (!point.contains(questionPoint)){
                point.add(questionPoint);
            }
        }
//        System.out.println("知识点列表"+point.size());
        double pointCoverage =(double) point.size()/(double) pointNumber;
//        System.out.println("知识点覆盖度"+pointCoverage);
        return pointCoverage;
    }



    // 获取试卷编号字符串
    public String getBest() {
        String str = "";
        for (int i = 0; i < selectedQuestions.length; i++) {
            str += String.valueOf(questionList.get(selectedQuestions[i]).getQuestionId()) + ",";
        }
//        System.out.println("试题编号" + str);

        return str;
    }
    // 将试卷编号字符串转换为试卷
    public Paper getPaper(String str) {

        String[] split = str.split(",");

        List<String> group1 = new ArrayList<>();
        List<String> group2 = new ArrayList<>();
        List<String> group3 = new ArrayList<>();
        List<String> group4 = new ArrayList<>();
        List<String> group5 = new ArrayList<>();

        int a1 = paperTemplate.getChoiceCount();
        int a2 = paperTemplate.getChoiceCount()+paperTemplate.getMultiCount();
        int a3 = paperTemplate.getChoiceCount()+paperTemplate.getMultiCount()+paperTemplate.getBlankFillingCount();
        int a4 =  paperTemplate.getChoiceCount()+paperTemplate.getMultiCount()+paperTemplate.getBlankFillingCount()+paperTemplate.getJudgeCount();
//        int a5 = ;

        for (int i = 0; i < split.length; i++) {
            if (i < a1) {
                group1.add(split[i]);
            } else  if (i < a2) {
                group2.add(split[i]);
            } else  if (i < a3) {
                group3.add(split[i]);
            } else  if (i < a4) {
                group4.add(split[i]);
            } else{
                group5.add(split[i]);
            }
        }
        Paper paper = new Paper();
        paper.setChoiceQuestion(String.join(",", group1));
        paper.setMultiQuestion(String.join(",", group2));
        paper.setBlankFillingQuestion(String.join(",", group3));
        paper.setJudgeQuestion(String.join(",", group4));
        paper.setShortQuestion(String.join(",", group5));
//        System.out.println("试卷"+paper);
        return paper;
    }

    
    // 判断试题是否已选择
    public boolean containsQuestion(int index) {
        for (int i = 0; i < selectedQuestions.length; i++) {
           return selectedQuestions[i]==index;
        }
        return false;
    }

    //试卷转为数组
    public int[] change(Paper paper){
//        System.out.println(paper);

        String[] split1 = paper.getChoiceQuestion().split(",");
        String[] split2 = paper.getMultiQuestion().split(",");
        String[] split3 = paper.getJudgeQuestion().split(",");
        String[] split4 = paper.getBlankFillingQuestion().split(",");
        String[] split5 = paper.getShortQuestion().split(",");

        List<String> mergedList = new ArrayList<>();
        if (!paper.getChoiceQuestion().isEmpty()){
            mergedList.addAll(Arrays.asList(split1));
        }

        if (!paper.getMultiQuestion().isEmpty()){
            mergedList.addAll(Arrays.asList(split2));
        }
        if (!paper.getJudgeQuestion().isEmpty()){
            mergedList.addAll(Arrays.asList(split3));
        }
        if (!paper.getBlankFillingQuestion().isEmpty()){
            mergedList.addAll(Arrays.asList(split4));
        }
        if (!paper.getShortQuestion().isEmpty()){
            mergedList.addAll(Arrays.asList(split5));
        }

        String[] split =  mergedList.toArray(new String[0]);

//        System.out.println(Arrays.toString(split));

        int[] intArray = new int[split.length];

        // 将字符串数组转换为整数数组
        for (int i = 0; i < split.length; i++) {
            try{
                intArray[i] = Integer.parseInt(split[i]);
            }catch (Exception e){
                continue;
            }
        }
        return intArray;
    }
      //计算交集占比并集
    public static double calculateCommonElementsRatio(int[] arrayA, int[] arrayB, int[] arrayC) {

        Set<Integer> uniqueElements = new HashSet<>();
        int duplicateCount = 0;

      // 遍历第一个数组，将元素添加到集合中
        for (int num : arrayA) {
            uniqueElements.add(num);
        }

      // 遍历第二个数组，统计重复元素个数
        for (int num : arrayB) {
            if (uniqueElements.contains(num)) {
                duplicateCount++;
            }
        }

     // 遍历第三个数组，统计重复元素个数
        for (int num : arrayC) {
            if (uniqueElements.contains(num)) {
                duplicateCount++;
            }
        }

        double totalElements = arrayA.length + arrayB.length + arrayC.length;
        double duplicateRatio = (double) duplicateCount / totalElements;

//        System.out.println("相同元素占总数的比例: " + duplicateRatio);
        return  duplicateRatio;
    }


}





