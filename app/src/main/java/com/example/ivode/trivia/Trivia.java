package com.example.ivode.trivia;

import android.util.Log;

import java.util.ArrayList;

public class Trivia {

    private ArrayList<Question> questions;
    private int points;
    private int question_number;
    private Question current_question;

    public Trivia(ArrayList<Question> questions) {
        this.questions = questions;
        this.current_question = questions.get(0);
    }

    public int getPoints() {
        return points;
    }

    public int getQuestion_number() {
        return question_number;
    }

    public Question getCurrent_question() {
        return current_question;
    }

    public Question getNext_question() {
        if (question_number >= questions.size()) {
            return null;
        }
        Question next_question = questions.get(question_number);
        current_question = next_question;
        question_number++;
        return next_question;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setQuestion_number(int question_number) {
        this.question_number = question_number;
    }

    public void setCurrent_question(Question current_question) {
        this.current_question = current_question;
    }

    public void answered_correctly() {
        this.points++;
    }
}