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

public class MainActivity extends AppCompatActivity implements TriviaRequest.Callback {

    private int number_of_questions = 10;
    private String question_type = "boolean";
    private ArrayList<Question> questions;
    private Trivia trivia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TriviaRequest triviaRequest = new TriviaRequest(this, number_of_questions, question_type);
        triviaRequest.getQuestions(this);
    }

    @Override
    public void gotQuestions(ArrayList<Question> questions) {
        this.questions = questions;

        Button trueButton = findViewById(R.id.correct);
        Button falseButton = findViewById(R.id.incorrect);
        trueButton.setOnClickListener(new checkAnswer(true));
        falseButton.setOnClickListener(new checkAnswer(false));

        this.trivia = new Trivia(questions);
        showNextQuestion();
    }

    @Override
    public void gotQuestionsError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private class checkAnswer implements View.OnClickListener {

        private Boolean given_answer;

        private checkAnswer(Boolean isButtonTrue) {
            this.given_answer = isButtonTrue;
        }

        @Override
        public void onClick(View v) {
            String answer = trivia.getCurrent_question().getCorrectAnswer();
            String answer_response;
            if (given_answer) {
                if (answer.equals("True")) {
                    answer_response = "Correct!";
                    trivia.answered_correctly();
                }
                else {
                    answer_response = "Incorrect!";
                }
            }
            else {
                if (answer.equals("False")) {
                    answer_response = "Correct!";
                    trivia.answered_correctly();
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
        if (trivia.getNext_question() == null) {
            Toast.makeText(getApplicationContext(), "All questions answered", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, StartActivity.class);
            intent.putExtra("points", "You scored " + trivia.getPoints() + " out of 5");
            startActivity(intent);
        }
        else {
            TextView points_text = findViewById(R.id.points_text);
            TextView question_text = findViewById(R.id.question_text);
            String question = trivia.getNext_question().getQuestion();

            points_text.setText(Html.fromHtml("Points: " + trivia.getPoints(), 0));
            question_text.setText(Html.fromHtml(question, 0));
        }
    }
}
