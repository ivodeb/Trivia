package com.example.ivode.trivia;

import android.util.Log;

import java.util.ArrayList;

public class Trivia {

    private ArrayList<Question> questions;
    private int points;
    private int questionNumber;
    private Question currentQuestion;

    public Trivia(ArrayList<Question> questions) {
        this.questions = questions;
        try {
            this.currentQuestion = questions.get(0);
        }
        catch(ArrayIndexOutOfBoundsException e) {
            Log.e("OutOfBounds", "question out of bounds");
        }
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(Question currentQuestion) {
        this.currentQuestion = currentQuestion;
    }

    public void answeredSuccesfully() {
        this.points++;
    }

    public Question getNextQuestion() {
        if (questionNumber >= questions.size()) {
            return null;
        }
        Question nextQuestion = questions.get(questionNumber);
        currentQuestion = nextQuestion;
        questionNumber++;
        return nextQuestion;
    }
}