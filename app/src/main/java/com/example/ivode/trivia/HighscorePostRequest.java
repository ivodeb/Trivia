package com.example.ivode.trivia;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

/** Posts the user's score and name. */
public class HighscorePostRequest extends StringRequest {

    private String highscore_name;
    private int highscore_points;

    HighscorePostRequest(int method, String url, Response.Listener<String> listener, Response.ErrorListener error_listener) {
        super(method, url, listener, error_listener);
    }

    @Override
    protected Map<String, String> getParams(){
        Map<String, String> params = new HashMap<>();
        params.put("name", highscore_name);
        params.put("score", String.valueOf(highscore_points));
        return params;
    }

    void postHighscore(String name, int points) {
        highscore_name = name;
        highscore_points = points;
    }
}
