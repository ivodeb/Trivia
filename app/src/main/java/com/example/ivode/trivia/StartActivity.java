package com.example.ivode.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Intent intent = getIntent();
        String points_scored = intent.getStringExtra("points");

        if (points_scored != null) {
            TextView score = findViewById(R.id.score);
            TextView play_button = findViewById(R.id.play_button);

            score.setText("You scored " + points_scored + " last round!");
            play_button.setText("Play again?");
        }
    }

    public void onStartClicked(View view) {
        Intent intent = new Intent(StartActivity.this, MainActivity.class);
        startActivity(intent);
    }
}