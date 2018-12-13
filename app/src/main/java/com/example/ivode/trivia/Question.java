package com.example.ivode.trivia;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {

    private String category, difficulty, question, questionType, correctAnswer;
    private ArrayList<String> incorrectAnswers;

    public Question(String category, String difficulty, String question, String questionType, String correctAnswer, ArrayList<String> incorrectAnswers) {
        this.category = category;
        this.difficulty = difficulty;
        this.question = question;
        this.questionType = questionType;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
    }

    // getters and setters
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public ArrayList<String> getIncorrectAnswers() {
        return incorrectAnswers;
    }

    public void setIncorrectAnswers(ArrayList<String> incorrectAnswers) {
        this.incorrectAnswers = incorrectAnswers;
    }
}