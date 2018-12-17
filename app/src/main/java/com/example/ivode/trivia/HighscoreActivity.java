package com.example.ivode.trivia;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

/** Let's the player fill in a name to post their highscore. Then shows the current leaderboard.*/
public class HighscoreActivity extends AppCompatActivity implements HighscoreRequest.CallbackHighscore, Response.Listener, Response.ErrorListener {

    private int my_highscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        Intent intent = getIntent();
        my_highscore = intent.getIntExtra("highscore", 0);
        TextView score = findViewById(R.id.points_text);
        score.setText("New highscore: " + String.valueOf(my_highscore) + "! ");
    }

    @Override
    public void onResponse(Object response) {
        HighscoreRequest highscoreRequest = new HighscoreRequest(this);
        highscoreRequest.getHighscores(this);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void gotHighscores(ArrayList<Highscore> highscores) {
        HighscoreAdapter adapter = new HighscoreAdapter(this, R.layout.highscore, highscores);
        ListView listView = findViewById(R.id.highscores_list);
        listView.setAdapter(adapter);
    }

    @Override
    public void gotHighscoresError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void onPostClicked(View v) {
        TextInputEditText input_name = findViewById(R.id.input);
        String name = input_name.getText().toString();

        // my server on CS50 IDE, won't work if not running
        String url = "https://ide50-ivodeb.cs50.io:8080/highscores";
        RequestQueue queue = Volley.newRequestQueue(this);
        HighscorePostRequest request = new HighscorePostRequest(Request.Method.POST, url, this, this);
        request.postHighscore(name, my_highscore);
        queue.add(request);

        // no need to make another activity for the highscore list, just remove the input views
        Button post_highscore_button = findViewById(R.id.post);
        TextInputLayout input_view = findViewById(R.id.input_view);
        post_highscore_button.setVisibility(View.GONE);
        input_view.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(HighscoreActivity.this, StartActivity.class);
        intent.putExtra("points", "" + my_highscore);
        startActivity(intent);
    }

    public void onHomeClicked(View v) {
        onBackPressed();
    }
}