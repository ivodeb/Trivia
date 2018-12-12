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

public class TriviaRequest implements Response.Listener<JSONObject>, Response.ErrorListener {

    private Context context;
    private ArrayList<Question> questions= new ArrayList<>();
    private int number_of_questions;
    private String question_type;

    Callback activity;

    public interface Callback {
        void gotQuestions(ArrayList<Question> questions);
        void gotQuestionsError(String message);
    }

    public TriviaRequest(Context c, int number_of_questions, String question_type) {
        this.context = c;
        this.number_of_questions = number_of_questions;
        this.question_type = question_type;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        activity.gotQuestionsError(error.getMessage());
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONArray questionArray = response.getJSONArray("results");

            for (int i = 0; i < questionArray.length(); i++) {
                JSONObject questionObject = questionArray.getJSONObject(i);
                String category = questionObject.getString("category");
                String questionType = questionObject.getString("type");
                String difficulty = questionObject.getString("difficulty");
                String question = questionObject.getString("question");
                String correctAnswer = questionObject.getString("correct_answer");

                ArrayList<String> incorrectAnswers = new ArrayList<>();
                JSONArray incorrectAnswersArray = questionObject.getJSONArray("incorrect_answers");
                for (int j = 0; j < incorrectAnswersArray.length(); j++) {
                    incorrectAnswers.add(incorrectAnswersArray.getString(j));
                }

                Question retrievedQuestion = new Question(category, difficulty, question,
                        questionType, correctAnswer,
                        incorrectAnswers);
                questions.add(retrievedQuestion);
            }
        }
        catch(JSONException error) {
            Log.e("requestError", error.getMessage());
        }
        activity.gotQuestions(questions);
    }

    void getQuestions(Callback act) {
        this.activity = act;

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://opentdb.com/api.php?amount=" + number_of_questions + "&type=" + question_type;

        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, this, this);
            queue.add(jsonObjectRequest);
        }
        catch(Exception error) {
            Log.e("requestError", error.getMessage());
        }
    }
}