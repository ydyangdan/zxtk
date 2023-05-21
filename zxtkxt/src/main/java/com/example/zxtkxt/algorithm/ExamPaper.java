package com.example.zxtkxt.algorithm;

import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class ExamPaper {
    private String name;
    private List<Question> questions;

    public ExamPaper(String name) {
        this.name = name;
        this.questions = new ArrayList<>();
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public void removeQuestion(Question question) {
        questions.remove(question);
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public double getDifficulty() {
        double difficulty = 0.0;
        for (Question question : questions) {
            difficulty += question.getDifficulty();
        }
        return difficulty;
    }

    public Set<String> calculateKnowledgePoints() {
        Set<String> knowledgePoints = new HashSet<>();
        for (Question question : questions) {
            knowledgePoints.addAll(question.getKnowledgePoints());
        }
        return knowledgePoints;
    }

    public String getName() {
        return name;
    }

    public static ExamPaper generateExam(Set<String> knowledgePoints, List<Question> questions, int numberOfQuestions) {
        ExamPaper examPaper = new ExamPaper("Exam Paper");
        Random random = new Random();

        // Keep adding questions until the required number of questions is reached
        while (examPaper.getQuestions().size() < numberOfQuestions) {
            // Filter the questions by the required knowledge points
            List<Question> filteredQuestions = questions.stream()
                    .filter(q -> q.getKnowledgePoints().containsAll(knowledgePoints))
                    .collect(Collectors.toList());

            // If there are no questions matching the required knowledge points, exit the loop
            if (filteredQuestions.isEmpty()) {
                break;
            }

            // Select a random question from the filtered list and add it to the exam paper
            Question question = filteredQuestions.get(random.nextInt(filteredQuestions.size()));
            examPaper.addQuestion(question);
        }

        return examPaper;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Exam Paper: ").append(name).append("\n");
        sb.append("Difficulty: ").append(getDifficulty()).append("\n");
        sb.append("Knowledge Points: ").append(calculateKnowledgePoints()).append("\n");
        sb.append("Questions:\n");
        for (int i = 0; i < questions.size(); i++) {
            sb.append(i + 1).append(". ").append(questions.get(i)).append("\n");
        }
        return sb.toString();
    }
}
