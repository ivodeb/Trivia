package com.example.ivode.trivia;

import java.io.Serializable;
import java.util.ArrayList;

/** Serializable trivia with list of questions, points, question nr and current question. */
public class Trivia implements Serializable {

    private ArrayList<Question> questions;
    private int points;
    private int question_number;
    private Question current_question;

    Trivia(ArrayList<Question> questions) {
        this.questions = questions;
        this.current_question = questions.get(0);
    }

    int getPoints() {
        return points;
    }

    public int getQuestion_number() {
        return question_number;
    }

    public void setQuestion_number(int question_number) {
        this.question_number = question_number;
    }

    Question getCurrent_question() {
        return current_question;
    }

    Question getNext_question() {
        if (question_number >= questions.size()) {
            return null;
        }
        Question next_question = questions.get(question_number);
        current_question = next_question;
        question_number++;
        return next_question;
    }

    void answered_correctly() {
        this.points++;
    }
}