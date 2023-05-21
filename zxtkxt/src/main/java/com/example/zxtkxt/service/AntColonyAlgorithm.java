package com.example.zxtkxt.service;
import com.example.zxtkxt.algorithm.ExamPaper;
import com.example.zxtkxt.pojo.AlgorithmParameters;
import com.example.zxtkxt.pojo.Paper;
import com.example.zxtkxt.pojo.PaperTemplate;
import com.example.zxtkxt.pojo.Question;
import lombok.Data;

import java.util.*;

@Data
public class AntColonyAlgorithm {

    private int numAnts = 50; // 蚂蚁数量
    private int maxIterations = 200; // 最大迭代次数
    private double pheromoneDecay = 0.8; // 信息素挥发系数
    private double alpha=0.8; // 信息素启发式因子
    private double beta=0.6; // 路径长度因子
    private double q = 1.0; //信息素增加强度系数
    private PaperTemplate paperTemplate = null ;

    private int numQuestions=100; // 试题数量
    private int numTypes=5; // 试题类型数量

    private double targetDifficulty; // 目标难度值
    private double targetPointCoverage; //知识点覆盖度
    private double targetRepeatability; //试卷相似度
    private Map<String, Integer> targetTypeCount; // 目标各题型数量

    private List<Question> questions; // 所有试题

    List<Paper> paperlist; //最近2个试卷
    int pointNumber = 13; //该课程知识点个数

    private Map<String, List<Integer>> typeMap; // 按类型分类的试题列表
    private Random random; // 随机数生成器

    double[][] pheromone; //信息素矩阵

    public AntColonyAlgorithm(){}

    public AntColonyAlgorithm(AlgorithmParameters parameters, PaperTemplate paperTemplate, Map<String, Integer> targetTypeCount, List<Question> questions, List<Paper> paperlist, int pointNumber) {
        this.numAnts=parameters.getAntCount();
        this.maxIterations=parameters.getIterationCount();
        this.alpha=parameters.getAlpha();
        this.beta=parameters.getBeta();
        this.pheromoneDecay=parameters.getRho();
        this.paperTemplate=paperTemplate;
        this.numQuestions = questions.size();
        this.targetDifficulty = paperTemplate.getDifficulty();
        this.targetPointCoverage=paperTemplate.getPointCoverage();
        this.targetRepeatability=paperTemplate.getRepeatability();
        this.targetTypeCount = targetTypeCount;
        this.questions = questions;
        this.random = new Random();
        this.paperlist=paperlist;
        this.pointNumber=pointNumber;

        this.typeMap = new LinkedHashMap<>();
        typeMap.put("单选题",new ArrayList<>());
        typeMap.put("多选题",new ArrayList<>());
        typeMap.put("填空题",new ArrayList<>());
        typeMap.put("判断题",new ArrayList<>());
        typeMap.put("简答题",new ArrayList<>());
        for (int i = 0; i < questions.size(); i++) {
            if (Objects.equals(questions.get(i).getQuestionType(), "单选题")){
                typeMap.get("单选题").add(i);
            }else if (Objects.equals(questions.get(i).getQuestionType(), "多选题")){
                typeMap.get("多选题").add(i);
            }else if (Objects.equals(questions.get(i).getQuestionType(), "填空题")){
                typeMap.get("填空题").add(i);
            }else if (Objects.equals(questions.get(i).getQuestionType(), "判断题")){
                typeMap.get("判断题").add(i);
            }else if (Objects.equals(questions.get(i).getQuestionType(), "简答题")){
                typeMap.get("简答题").add(i);
            }
        }

    }

    public AntColonyAlgorithm( PaperTemplate paperTemplate, Map<String, Integer> targetTypeCount, List<Question> questions, List<Paper> paperlist,int pointNumber) {

        this.paperTemplate=paperTemplate;
        this.numQuestions = questions.size();
        this.targetDifficulty = paperTemplate.getDifficulty();
        this.targetPointCoverage=paperTemplate.getPointCoverage();
        this.targetRepeatability=paperTemplate.getRepeatability();
        this.targetTypeCount = targetTypeCount;
        this.questions = questions;
        this.random = new Random();
        this.paperlist=paperlist;
        this.pointNumber=pointNumber;

        this.typeMap = new LinkedHashMap<>();
        typeMap.put("单选题",new ArrayList<>());
        typeMap.put("多选题",new ArrayList<>());
        typeMap.put("填空题",new ArrayList<>());
        typeMap.put("判断题",new ArrayList<>());
        typeMap.put("简答题",new ArrayList<>());
        for (int i = 0; i < questions.size(); i++) {
            if (Objects.equals(questions.get(i).getQuestionType(), "单选题")){
                typeMap.get("单选题").add(i);
            }else if (Objects.equals(questions.get(i).getQuestionType(), "多选题")){
                typeMap.get("多选题").add(i);
            }else if (Objects.equals(questions.get(i).getQuestionType(), "填空题")){
                typeMap.get("填空题").add(i);
            }else if (Objects.equals(questions.get(i).getQuestionType(), "判断题")){
                typeMap.get("判断题").add(i);
            }else if (Objects.equals(questions.get(i).getQuestionType(), "简答题")){
                typeMap.get("简答题").add(i);
            }
        }

    }


    /**
     * 蚁群算法
     */
    public Paper solve() {
        // 初始化信息素矩阵
        pheromone = new double[numQuestions][numQuestions];
        for (int i = 0; i < numQuestions; i++) {
            for (int j = 0; j < numQuestions; j++) {
//                pheromone[i][j] = 1.0 / numQuestions;
                pheromone[i][j] = 1.0;
            }
        }


        double bestFitness = Double.MAX_VALUE;
//        Paper bestPaper = new Paper();
        String bestStr = "";
        double finallyDifficulty = 0.0;
        double finallyRepeatability = 0.0;
        double finallyPointCoverage = 0.0;
        // 迭代搜索
        for (int iter = 0; iter < maxIterations; iter++) {
            System.out.println("第"+iter+"迭代：++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            // 初始化蚂蚁
            List<Ant> ants = new ArrayList<>();
            for (int i = 0; i < numAnts; i++) {
                ants.add(new Ant(paperTemplate,questions,paperlist,pointNumber));
            }

            // 搜索路径
            for (Ant ant : ants) {
                //按题型搜索
                for (Map.Entry<String, Integer> entry : targetTypeCount.entrySet()) {
                    //题型名称
                    String key = entry.getKey();
                    //对应题型的数量
                    Integer value = entry.getValue();
                    if (value==0) {
                        continue;
                    }
                    //对应题型的试题列表
                    List<Integer> values = typeMap.get(key);
//                    System.out.println("搜索"+key + ": " + value+values);

                    //每一个题型搜索指定数量的试题
                    for (Integer i = 0; i < value; i++) {

                        //选择下一个试题
                        int nextQuestion = chooseNextQuestion(values,ant.getSelectedQuestions());

                        //加入已选择的试题列表
                        ant.add(nextQuestion);
                    }
                }
//                System.out.println("蚂蚁已选择的试题列表"+Arrays.toString(ant.getSelectedQuestions()));
            }

            double delta1 = 0.0;
            // 更新信息素矩阵
            for (int i = 0; i < numQuestions; i++) {
                for (int j = 0; j < numQuestions; j++) {
                    double delta = 0;
                    for (Ant ant : ants) {
                        if (ant.containsQuestion(i) && ant.containsQuestion(j)) {
                            delta += q / ant.calculateFitness();
                        }
                    }
                    pheromone[i][j] = (1 - pheromoneDecay) * pheromone[i][j] + delta;

                }
            }


            double oldFitness = bestFitness;
            // 更新最优解
            for (Ant ant : ants) {
                if (ant.calculateFitness() < bestFitness && ant.isConstraint()) {
                    bestFitness = ant.calculateFitness();
                    bestStr=ant.getBest();
                    finallyDifficulty = ant.calculateDifficulty();
                    finallyRepeatability = ant.calculateRepeatability();
                    finallyPointCoverage = ant.calculatePointCoverage();
                }
            }
            System.out.println("本次迭代蚂蚁的最好适应度"+bestFitness);
            System.out.println("本次迭代蚂蚁的最终难度"+finallyDifficulty);
            System.out.println("本次迭代蚂蚁的最终相似度"+finallyRepeatability);
            System.out.println("本次迭代蚂蚁的最终知识点覆盖度"+finallyPointCoverage);

            //提前结束
            if (oldFitness==bestFitness&&iter>maxIterations/4){
                System.out.println("提前结束");
                break;
            }

        }

        System.out.println("最优试题编号"+bestStr);

        Paper bestPaper = new Ant(paperTemplate).getPaper(bestStr);
        bestPaper.setRepeatability(finallyRepeatability);
        bestPaper.setPointCoverage(finallyPointCoverage);
        bestPaper.setPaperLevel(finallyDifficulty);
        bestPaper.setPaperName(paperTemplate.getPaperName());
        bestPaper.setCourseName(paperTemplate.getCourseName());
        return bestPaper;
    }


    /**
     * 选择下一个试题
     *
     * @param values            试题列表
     * @param selectedQuestions 已选择的试题列表
     * @return 下一个试题的索引
     */
    public int chooseNextQuestion(List<Integer> values, int[] selectedQuestions) {
       //该题型的试题数量
        int numOfQuestions = values.size();
        //已选择的试题数量
        int selectedNum = getLength(selectedQuestions);

        List<Double> probabilities = new ArrayList<>();
        double totalProbability = 0.0;

        // 计算未选择试题的选择概率
        for (int i = 0; i < numOfQuestions; i++) {

            if (!contains(selectedQuestions, i)) {
                //试题难度的倒数
                double v = 1.0 / questions.get(values.get(i)).getDifficulty();
                double pheromoneLevel = pheromone[selectedNum][i];
                double attractiveness = Math.pow(v, beta);
                double probability = Math.pow(pheromoneLevel, alpha) * attractiveness;
                probabilities.add(probability);
                totalProbability += probability;
            }else {
                // 如果已经选择了此试题，则概率置为0
                probabilities.add(0.0);
            }
        }
        // 轮盘赌选择下一个试题
        Random random = new Random();
        double randomValue = random.nextDouble() * totalProbability;
        //累积概率
        double cumulativeProbability = 0.0;

        for (int i = 0; i < numOfQuestions; i++) {
            //检测是否已经选择
            if (!contains(selectedQuestions, values.get(i))) {
                cumulativeProbability += probabilities.get(i);
                if (randomValue <= cumulativeProbability) {
                    return values.get(i);
                }
            }
        }

        // 如果未能选择下一个试题，则随机选择一个未选择试题
        int randomIndex = random.nextInt(numOfQuestions);
        while (contains(selectedQuestions, values.get(randomIndex))) {
            randomIndex = random.nextInt(numOfQuestions);
        }

        return values.get(randomIndex);
    }
    /**
     * 检查数组中是否包含指定值
     *
     * @param array 数组
     * @param value 要检查的值
     * @return 是否包含指定值
     */
    private boolean contains(int[] array, int value) {
        for (int num : array) {
            if (num == value) {
                return true;
            }
        }
        return false;
    }

    //获取已选择的试题数量
    public int getLength(int[] selectedQuestions){
        int t=0;
        for (int i = 0; i < selectedQuestions.length; i++) {
            if (selectedQuestions[i]!=-1){
                t+=1;
            }
        }
        return t;
    }


}