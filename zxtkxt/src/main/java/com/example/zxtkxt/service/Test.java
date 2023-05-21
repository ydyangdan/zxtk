package com.example.zxtkxt.service;

import com.example.zxtkxt.pojo.Paper;
import com.example.zxtkxt.pojo.Question;

import java.util.ArrayList;
import java.util.List;

public class Test {
//    private int[] questions; // 蚂蚁已选择的试题
//    private int[] types; // 蚂蚁已选择的试题类型
//    private int[] typeCount; // 蚂蚁已选择的各种试题类型的数量
//    private double fitness; // 蚂蚁所组成试卷的适应度
//    private List<Question> questionList; // 所有试题列表
//    private int numTypes; // 试题类型数量
//    private int numQuestions; // 总试题数量
//
//    public Ant(int numQuestions, int numTypes) {
//        this.numQuestions = numQuestions;
//        this.numTypes = numTypes;
//        questions = new int[numQuestions];
//        types = new int[numQuestions];
//        typeCount = new int[numTypes];
//        questionList = new ArrayList<>();
//        fitness = Double.MAX_VALUE;
//    }
//
//    // 选择下一道试题
//    public void chooseNextQuestion(int type) {
//        // 获取当前未选择的试题列表
//        List<Integer> candidates = new ArrayList<>();
//        for (int i = 0; i < numQuestions; i++) {
//            if (questions[i] == 0 && types[i] == type) {
//                candidates.add(i);
//            }
//        }
//
//        // 选择试题
//        if (candidates.size() > 0) {
//            int index = select(candidates);
//            questions[candidates.get(index)] = 1;
//            types[candidates.get(index)] = type;
//            typeCount[type]++;
//        }
//    }
//
//    // 计算适应度
//    public double calculateFitness(List<Question> questions, double difficultyWeight, double diversityWeight) {
//        double difficulty = 0;
//        double diversity = 0;
//        int[] typeCount = new int[numTypes];
//        for (int i = 0; i < numQuestions; i++) {
//            if (this.questions[i] == 1) {
//                difficulty += questions.get(i).getDifficulty();
//                typeCount[types[i]]++;
//            }
//        }
//        for (int i = 0; i < numTypes; i++) {
//            if (typeCount[i] > 0) {
//                diversity++;
//            }
//        }
//        fitness = difficultyWeight * difficulty + diversityWeight * diversity;
//        return fitness;
//    }
//
//    // 获取试卷
//    public Paper getExamPaper() {
//        List<Question> selected = new ArrayList<>();
//        for (int i = 0; i < numQuestions; i++) {
//            if (questions[i] == 1) {
//                selected.add(questionList.get(i));
//            }
//        }
//        Paper paper = new Paper();
////        new Paper(selected)
//        return paper;
//    }
//
//    // 判断试题是否已选择
//    public boolean containsQuestion(int index) {
//        return questions[index] == 1;
//    }
//
//    // 获取已选择的最后一道试题
//    public int getLastQuestion() {
//        for (int i = numQuestions - 1; i >= 0; i--) {
//            if (questions[i] == 1) {
//                return i;
//            }
//        }
//        return -1;
//    }
//
//
//    /**
//     * 获取某种试题类型已选择的数量
//     *
//     * @param type 试题类型
//     * @return 选择的数量
//     */
//    public int getTypeCount(int type) {
//        int count = 0;
//        Paper paper = new Paper();
////        for (Question q : paper.getQuestions()) {
////            if (q.getType() == type) {
////                count++;
////            }
////        }
//        return count;
//    }
//
//    public int select(List<Integer> type) {
//        int count = 0;
//
//        return count;
//    }
//
//
//    // 搜索路径
//            for (int k = 0; k < numQuestions; k++) {
//        for (Ant ant : ants) {
//            // 计算选择概率
//            int q = ant.getLastQuestion();
//            double[] prob = new double[numTypes];
//            double sum = 0;
//            for (int t = 0; t < numTypes; t++) {
//                if (ant.getTypeCount(t) >= numQuestions / numTypes) {
//                    // 该类型试题已满足数量要求
//                    prob[t] = 0;
//                } else {
//                    double p = Math.pow(pheromone[q][t], alpha) *
//                            Math.pow(1.0 / questions.get(q).getDifficulty(), beta);
//                    prob[t] = p;
//                    sum += p;
//                }
//            }
//            if (sum == 0) {
//                // 所有类型试题数量都已满足要求
//                prob = new double[numTypes];
//                // 对未被选择的类型试题，重新计算概率并归一化
//                for (int t = 0; t < numTypes; t++) {
//                    if (ant.getTypeCount(t) >= numQuestions / numTypes) {
//                        // 该类型试题已满足数量要求
//                        prob[t] = 0;
//                    } else {
//                        double p = Math.pow(pheromone[q][t], alpha) *
//                                Math.pow(1.0 / questions.get(q).getDifficulty(), beta);
//                        prob[t] = p;
//                        sum += p;
//                    }
//                }
//                for (int t = 0; t < numTypes; t++) {
//                    prob[t] /= sum;
//                }
//            }
//
//            // 选择下一道试题
//            int type = select(prob);
//            ant.chooseNextQuestion(type);
//        }
//    }
}
