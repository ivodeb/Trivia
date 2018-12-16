package com.example.ivode.trivia;

import java.io.Serializable;

/** Serializable question with category, question and correct answer. */
public class Question implements Serializable {

    private String category, question, correct_answer;

    Question(String category, String question, String correct_answer) {
        this.category = category;
        this.question = question;
        this.correct_answer = correct_answer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    String getQuestion() {
        return question;
    }

    String getCorrectAnswer() {
        return correct_answer;
    }
}