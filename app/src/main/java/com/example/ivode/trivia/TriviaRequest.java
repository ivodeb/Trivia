package com.example.ivode.trivia;

import android.content.Context;
import android.util.Log;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/** Request a trivia from API, currently only with true/false questions and normal difficulty! */
public class TriviaRequest implements Response.Listener<JSONObject>, Response.ErrorListener {

    private Context context;
    private ArrayList<Question> questions= new ArrayList<>();
    private int number_of_questions;
    private String question_type;

    // callback from another activity
    Callback activity;
    public interface Callback {
        void gotQuestions(ArrayList<Question> questions);
        void gotQuestionsError(String message);
    }

    TriviaRequest(Context context, int number_of_questions, String question_type) {
        this.context = context;
        this.number_of_questions = number_of_questions;
        this.question_type = question_type;
    }

    void getQuestions(Callback activity) {
        this.activity = activity;
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://opentdb.com/api.php?amount=" + number_of_questions + "&type=" + question_type;

        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, this, this);
            queue.add(jsonObjectRequest);
        }
        catch(Exception error) {
            Log.e("error", error.getMessage());
        }
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONArray questionArray = response.getJSONArray("results");

            for (int i = 0; i < questionArray.length(); i++) {
                JSONObject questionObject = questionArray.getJSONObject(i);
                String category = questionObject.getString("category");
                String question = questionObject.getString("question");
                String correct_answer = questionObject.getString("correct_answer");
                Question retrievedQuestion = new Question(category, question, correct_answer);
                questions.add(retrievedQuestion);
            }
        }
        catch(JSONException error) {
            Log.e("error", error.getMessage());
        }
        activity.gotQuestions(questions);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        activity.gotQuestionsError(error.getMessage());
    }
}