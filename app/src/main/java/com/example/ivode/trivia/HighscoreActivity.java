package com.example.ivode.trivia;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class HighscoreActivity extends AppCompatActivity implements HighscoreRequest.CallbackHighscore, Response.Listener, Response.ErrorListener {

    private int my_highscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        Intent intent = getIntent();
        my_highscore = intent.getIntExtra("highscore", 0);

        TextView score = findViewById(R.id.points_text);
        score.setText("New highscore: " + String.valueOf(my_highscore));
    }

    public void onSendClicked(View v) {
        TextInputEditText nameEdit = findViewById(R.id.nameInput);
        String name = "";
        try {
            name = nameEdit.getText().toString();
        }
        catch(Exception e) {
            Log.e("inputText", e.getMessage());
        }

        String url = "https://ide50-ivodeb.cs50.io:8080/list";
        RequestQueue queue = Volley.newRequestQueue(this);
        HighscorePostRequest request = new HighscorePostRequest(Request.Method.POST, url, this, this);
        request.provideParams(name, my_highscore);
        queue.add(request);

        Intent intent = new Intent(HighscoreActivity.this, StartActivity.class);
        intent.putExtra("points", "" + my_highscore);
        startActivity(intent);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(Object response) {
        HighscoreRequest highscoreRequest = new HighscoreRequest(this);
        highscoreRequest.getHighscores(this);
    }

    @Override
    public void gotHighscores(ArrayList<Highscore> highscores) {
        HighscoreAdapter adapter = new HighscoreAdapter(this, R.layout.highscore, highscores);
        ListView listView = findViewById(R.id._dynamic);
        listView.setAdapter(adapter);
    }

    @Override
    public void gotHighscoresError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(HighscoreActivity.this, StartActivity.class);
        intent.putExtra("points", "" + my_highscore);
        startActivity(intent);
    }
}