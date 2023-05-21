package com.example.zxtkxt.algorithm;

import java.util.*;

/**
 * 在上面的代码中，我们定义了两个类，ExamPaper和Question。
 *
 *         ExamPaper类表示试卷，包含一个名称和一个题目列表。Question类表示题目，包含一个名称、一个难度和一个知识点列表。
 *
 *         在GeneticAlgorithm类中，我们定义了一些变量来控制算法的行为：
 *
 *         questionBank：题库，是一个包含所有可用题目的列表。
 *         populationSize：种群大小，即每一代中包含的个体数量。
 *         crossoverRate：交叉概率，即两个个体进行交叉的概率。
 *         mutationRate：变异概率，即一个个体发生变异的概率。
 *         eliteRate：精英比例，即每一代中最优个体的比例。
 *         在run方法中，我们首先生成一个随机种群。然后，我们在每一代中进行以下操作：
 *
 *         计算每个个体的适应度。
 *         选择两个个体进行交叉，并添加新个体到新种群中。
 *         对新种群进行变异。
 *         选择一定比例的精英，并将其添加到新种群中。
 *         替换当前种群为新种群。
 *         打印每一代最优解的适应度。
 *         我们使用calculateFitness方法来计算一个试卷的适应度。适应度的计算方式包括试卷的总难度和知识点覆盖率。难度越低，知识点覆盖率越高，适应度越好。
 *
 *         在ExamPaper类中，我们使用calculateKnowledgePoints方法来计算试卷覆盖的知识点。这个方法会将每个题目的知识点加入到一个Set中，确保没有重复的知识点。
 *
 *         最后，在main方法中，我们创建了一个包含5个题目的题库，并使用遗传算法自动组卷，生成一个包含10个题目的试卷。我们输出了最终的试卷，即算法得到的最优解。
 */
// 定义遗传算法类
public class GeneticAlgorithm {
    private List<Question> questionBank;
    private int populationSize;
    private double mutationRate;
    private double crossoverRate;
    private double targetFitness;

    public GeneticAlgorithm(List<Question> questionBank, int populationSize, double mutationRate, double crossoverRate,double targetFitness) {
        this.questionBank = questionBank;
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.targetFitness = targetFitness;
    }

    // 初始化种群
    public List<ExamPaper> initializePopulation(int numPapers) {
        List<ExamPaper> population = new ArrayList<>();

        for (int i = 0; i < numPapers; i++) {
            ExamPaper paper = new ExamPaper("Paper " + i);
            List<Question> selectedQuestions = new ArrayList<>();

            // 从题库中随机选择试题
            for (Question question : questionBank) {
                if (Math.random() < 0.5) {
                    selectedQuestions.add(question);
                }
            }

            paper.getQuestions().addAll(selectedQuestions);
            paper.calculateKnowledgePoints();
            population.add(paper);
        }

        return population;
    }

    // 计算适应度
    public double calculateFitness(ExamPaper paper) {
        double fitness = 0;

        // 计算试卷的难度和知识点覆盖情况
        double totalDifficulty = 0;
        Map<String, Boolean> knowledgePoints = new HashMap<>();
        for (Question question : paper.getQuestions()) {
            totalDifficulty += question.getDifficulty();

            for (String knowledgePoint : question.getKnowledgePoints()) {
                knowledgePoints.put(knowledgePoint, true);
            }
        }

        // 计算知识点覆盖率
        double numCovered = 0;
        for (Boolean covered : knowledgePoints.values()) {
            if (covered) {
                numCovered++;
            }
        }
        double coverage = numCovered / knowledgePoints.size();

        // 适应度为试卷的难度和知识点覆盖率的加权平均值
        fitness = 0.6 * totalDifficulty + 0.4 * coverage;

        return fitness;
    }

    // 选择操作
    public ExamPaper select(List<ExamPaper> population) {
        double totalFitness = 0;
        for (ExamPaper paper : population) {
            totalFitness += calculateFitness(paper);
        }

        // 按适应度从大到小排序
        Collections.sort(population, (p1, p2) -> Double.compare(calculateFitness(p2), calculateFitness(p1)));

        // 按轮盘赌算法选择试卷
        double rouletteValue = Math.random() * totalFitness;
        double currentFitness = 0;
        for (ExamPaper paper : population) {
            currentFitness += calculateFitness(paper);
            if (currentFitness >= rouletteValue) {
                return paper;
            }
        }

        return population.get(0);
    }

    // 交叉操作
    public ExamPaper crossover(ExamPaper parent1, ExamPaper parent2) {
        ExamPaper child = new ExamPaper("Child");

        // 从两个父代中随机选择试题
        List<Question> selectedQuestions = new ArrayList<>();
        selectedQuestions.addAll(parent1.getQuestions());
        selectedQuestions.addAll(parent2.getQuestions());
        Collections.shuffle(selectedQuestions);

        // 将试题添加到子代中
        for (Question question : selectedQuestions) {
            if (child.getQuestions().size() < questionBank.size() && !child.getQuestions().contains(question)) {
                child.getQuestions().add(question);
            }
        }

        child.calculateKnowledgePoints();
        return child;
    }

    // 变异操作
    public void mutate(ExamPaper paper) {
        // 随机选择一道试题进行替换
        int index = (int) (Math.random() * paper.getQuestions().size());
        Question question = paper.getQuestions().get(index);
        int newIndex = (int) (Math.random() * questionBank.size());
        paper.getQuestions().set(index, questionBank.get(newIndex));
        paper.calculateKnowledgePoints();
    }


    // 运行遗传算法
    public ExamPaper run(int numGenerations) {
        // 初始化种群
        List<ExamPaper> population = initializePopulation(populationSize);

        // 迭代指定次数
        for (int i = 0; i < numGenerations; i++) {
            List<ExamPaper> newPopulation = new ArrayList<>();

            // 生成下一代
            while (newPopulation.size() < populationSize) {
                ExamPaper parent1 = select(population);
                if (Math.random() < crossoverRate) {
                    ExamPaper parent2 = select(population);
                    ExamPaper child = crossover(parent1, parent2);
                    if (Math.random() < mutationRate) {
                        mutate(child);
                    }
                    newPopulation.add(child);
                } else {
                    newPopulation.add(parent1);
                }
            }

            // 替换当前种群
            population = newPopulation;

            // 打印每一代最优解的适应度
            ExamPaper bestPaper = population.get(0);
            double bestFitness = calculateFitness(bestPaper);
            System.out.println("Generation " + i + ": " + bestFitness);

            // 如果最优解的适应度已经足够高，停止迭代
            if (bestFitness >= targetFitness) {
                return bestPaper;
            }
        }

        // 返回最后一代中最优解
        ExamPaper bestPaper = population.get(0);
        double bestFitness = calculateFitness(bestPaper);
        System.out.println("Final Generation: " + bestFitness);
        return bestPaper;
    }

    // 测试
    public static void main(String[] args) {
        List<Question> questionBank = new ArrayList<>();
        questionBank.add(new Question("Question 1", 0.8, Arrays.asList("Knowledge Point 1", "Knowledge Point 2")));
        questionBank.add(new Question("Question 2", 0.6, Arrays.asList("Knowledge Point 2", "Knowledge Point 3")));
        questionBank.add(new Question("Question 3", 0.9, Arrays.asList("Knowledge Point 1", "Knowledge Point 3")));
        questionBank.add(new Question("Question 4", 0.7, Arrays.asList("Knowledge Point 4")));
        questionBank.add(new Question("Question 5", 0.5, Arrays.asList("Knowledge Point 1", "Knowledge Point 3")));
        questionBank.add(new Question("Question 6", 0.5, Arrays.asList("Knowledge Point 5")));
        questionBank.add(new Question("Question 7", 0.6, Arrays.asList("Knowledge Point 5", "Knowledge Point 1")));
        questionBank.add(new Question("Question 8", 0.3, Arrays.asList("Knowledge Point 5")));
        questionBank.add(new Question("Question 9", 0.2, Arrays.asList("Knowledge Point 5", "Knowledge Point 2")));
        questionBank.add(new Question("Question 10", 0.1, Arrays.asList("Knowledge Point 4")));
        questionBank.add(new Question("Question 11", 0.8, Arrays.asList("Knowledge Point 1", "Knowledge Point 6")));

        GeneticAlgorithm algorithm = new GeneticAlgorithm(questionBank, 10, 0.7, 0.1, 0.9);
        ExamPaper bestPaper = algorithm.run(1000);


        System.out.println("Best paper:");
        System.out.println(bestPaper.toString());
    }
}


