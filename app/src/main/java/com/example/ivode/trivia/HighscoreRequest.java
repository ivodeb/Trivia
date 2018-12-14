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

public class HighscoreRequest implements Response.Listener<JSONArray>, Response.ErrorListener {

    private Context context;
    private ArrayList<Highscore> highscores = new ArrayList<>();

    CallbackHighscore activity;

    public interface CallbackHighscore {
        void gotHighscores(ArrayList<Highscore> highscores);
        void gotHighscoresError(String message);
    }

    public HighscoreRequest(Context context) {
        this.context = context;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        activity.gotHighscoresError(error.getMessage());
    }

    @Override
    public void onResponse(JSONArray response) {
        try {
            JSONArray highscoreArray = response;

            for (int i = 0; i < highscoreArray.length(); i++) {
                JSONObject highscoreObject = highscoreArray.getJSONObject(i);
                String name = highscoreObject.getString("name");
                String score = highscoreObject.getString("score");
                Highscore highscore = new Highscore(name, score);
                highscores.add(highscore);
            }
        }
        catch(JSONException error) {
            Log.e("requestError", error.getMessage());
        }
        activity.gotHighscores(highscores);
    }

    public void getHighscores(CallbackHighscore act) {
        this.activity = act;

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://ide50-s-terpstra.cs50.io/list";
        try {
            JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(url, this, this);
            queue.add(jsonObjectRequest);
        }
        catch(Exception error) {
            Log.e("requestError", error.getMessage());
        }
    }
}