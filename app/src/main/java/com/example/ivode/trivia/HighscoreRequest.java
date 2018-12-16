package com.example.ivode.trivia;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/** Request current highscores from server. */
public class HighscoreRequest implements Response.Listener<JSONArray>, Response.ErrorListener {

    private Context context;
    private ArrayList<Highscore> highscores = new ArrayList<>();

    // callback from another activity
    CallbackHighscore activity;
    public interface CallbackHighscore {
        void gotHighscores(ArrayList<Highscore> highscores);
        void gotHighscoresError(String message);
    }

    HighscoreRequest(Context context) {
        this.context = context;
    }

    void getHighscores(CallbackHighscore activity) {
        this.activity = activity;

        RequestQueue queue = Volley.newRequestQueue(context);
        // my server on CS50 IDE, won't work if not running
        String url = "https://ide50-ivodeb.cs50.io:8080/highscores";
        try {
            JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(url, this, this);
            queue.add(jsonObjectRequest);
        }
        catch(Exception error) {
            Log.e("requestError", error.getMessage());
        }
    }

    @Override
    public void onResponse(JSONArray response) {
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject highscore = response.getJSONObject(i);
                String name = highscore.getString("name");
                String score = highscore.getString("score");
                highscores.add(new Highscore(name, score));
            }
        }
        catch(JSONException error) {
            Log.e("requestError", error.getMessage());
        }
        activity.gotHighscores(highscores);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        activity.gotHighscoresError(error.getMessage());
    }
}