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

/** Shows the questions (only true-false questions for now) and allows the user to answer them and score points.*/
public class MainActivity extends AppCompatActivity implements TriviaRequest.Callback {

    int number_of_questions = 20;
    String question_type = "boolean";
    private Trivia trivia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // state restoration
        if (savedInstanceState != null) {
            this.trivia = (Trivia) savedInstanceState.getSerializable("trivia");
            TextView question_number = findViewById(R.id.question_number);
            TextView points_text = findViewById(R.id.points_text);
            TextView question_text = findViewById(R.id.question_text);
            String question = trivia.getCurrent_question().getQuestion();

            question_number.setText("Question: " + trivia.getQuestion_number()/2);
            points_text.setText(Html.fromHtml("Points: " + trivia.getPoints(), 0));
            question_text.setText(Html.fromHtml(question, 0));

            Button Correct = findViewById(R.id.correct);
            Button Incorrect = findViewById(R.id.incorrect);
            Correct.setOnClickListener(new checkAnswer(true));
            Incorrect.setOnClickListener(new checkAnswer(false));
        }
        else {
            TriviaRequest triviaRequest = new TriviaRequest(this, number_of_questions, question_type);
            triviaRequest.getQuestions(this);
        }
    }

    @Override
    public void gotQuestions(ArrayList<Question> questions) {
        Button Correct = findViewById(R.id.correct);
        Button Incorrect = findViewById(R.id.incorrect);
        Correct.setOnClickListener(new checkAnswer(true));
        Incorrect.setOnClickListener(new checkAnswer(false));

        this.trivia = new Trivia(questions);
        nextQuestion();
    }

    @Override
    public void gotQuestionsError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    // check answer and give respective toast, then show next question
    private class checkAnswer implements View.OnClickListener {

        private Boolean given_answer;
        private checkAnswer(Boolean correct_pressed) {
            this.given_answer = correct_pressed;
        }

        @Override
        public void onClick(View v) {
            String answer = trivia.getCurrent_question().getCorrectAnswer();
            if ((given_answer && answer.equals("True")) || (!given_answer && answer.equals("False"))) {
                Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_LONG).show();
                trivia.answered_correctly();
            }
            else {
                Toast.makeText(getApplicationContext(), "Incorrect!", Toast.LENGTH_LONG).show();
            }
            nextQuestion();
        }
    }

    // check if there is a next question; if not, go to highscores, else: call the question
    private void nextQuestion() {
        if (trivia.getNext_question() == null) {
            Intent intent = new Intent(MainActivity.this, HighscoreActivity.class);
            intent.putExtra("highscore", trivia.getPoints());
            startActivity(intent);
        }
        else {
            TextView question_number = findViewById(R.id.question_number);
            TextView points_text = findViewById(R.id.points_text);
            TextView question_text = findViewById(R.id.question_text);
            String question = trivia.getNext_question().getQuestion();

            question_number.setText("Question: " + trivia.getQuestion_number()/2);
            points_text.setText(Html.fromHtml("Points: " + trivia.getPoints(), 0));
            question_text.setText(Html.fromHtml(question, 0));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("trivia", trivia);
    }
}
