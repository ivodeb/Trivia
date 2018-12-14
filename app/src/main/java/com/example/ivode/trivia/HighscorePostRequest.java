package com.example.ivode.trivia;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class HighscorePostRequest extends StringRequest {

    private String name;
    private int points;

    public HighscorePostRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    public void provideParams(String name, int points) {
        this.name = name;
        this.points = points;
    }

    @Override
    protected Map<String, String> getParams(){
        Map<String, String> params = new HashMap<>();
        params.put("name", this.name);
        params.put("score", String.valueOf(this.points));
        return params;
    }
}
