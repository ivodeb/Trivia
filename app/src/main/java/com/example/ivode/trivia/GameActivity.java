package com.example.ivode.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity implements TriviaRequest.Callback {

    private int nQuestions = 10;
    private String typeOfQuestion = "boolean";
    private ArrayList<Question> questions;
    private Trivia trivia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TriviaRequest triviaRequest = new TriviaRequest(this, nQuestions, typeOfQuestion);
        triviaRequest.getQuestions(this);
    }

    @Override
    public void gotQuestions(ArrayList<Question> questions) {
        Toast.makeText(this, "Questions received!", Toast.LENGTH_LONG).show();
        this.questions = questions;

        Button trueButton = findViewById(R.id.trueButton);
        Button falseButton = findViewById(R.id.falseButton);
        trueButton.setOnClickListener(new TrueFalseButtonListener(true));
        falseButton.setOnClickListener(new TrueFalseButtonListener(false));

        this.trivia = new Trivia(questions);
        showNextQuestion();
    }

    @Override
    public void gotQuestionsError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private class TrueFalseButtonListener implements View.OnClickListener {

        private Boolean isButtonTrue;

        private TrueFalseButtonListener(Boolean isButtonTrue) {
            this.isButtonTrue = isButtonTrue;
        }

        @Override
        public void onClick(View v) {
            String correctAnswer = trivia.getCurrentQuestion().getCorrectAnswer();
            String answer_response;
            if (isButtonTrue) {
                if (correctAnswer.equals("True")) {
                    answer_response = "Correct!";
                    trivia.answeredSuccesfully();
                }
                else {
                    answer_response = "Incorrect!";
                }
            }
            else {
                if (correctAnswer.equals("False")) {
                    answer_response = "Correct!";
                    trivia.answeredSuccesfully();
                }
                else {
                    answer_response = "Incorrect!";
                }
            }
            Toast.makeText(getApplicationContext(), answer_response, Toast.LENGTH_LONG).show();
            showNextQuestion();
        }
    }

    private void showNextQuestion() {
        if (trivia.getNextQuestion() == null) {
            Toast.makeText(getApplicationContext(), "All questions answered", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(GameActivity.this, MenuActivity.class);
            startActivity(intent);
        }
        else {
            String question = trivia.getNextQuestion().getQuestion();
            TextView questionView = findViewById(R.id.questionTextView);
            questionView.setText(Html.fromHtml(question, 0));
        }
    }
}
