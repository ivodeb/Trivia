package com.example.ivode.trivia;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

/** Get the user's name and score and put them into a list. */
public class HighscoreAdapter extends ArrayAdapter<Highscore> {

    private ArrayList<Highscore> highscores;

    HighscoreAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Highscore> scores) {
        super(context, resource, scores);
        highscores = scores;
        Collections.sort(highscores);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.highscore, parent,
                    false);
        }
        TextView highscore_name = view.findViewById(R.id.highscore_name);
        TextView highscore_points = view.findViewById(R.id.highscore_points);
        highscore_name.setText(highscores.get(position).getName() + " ");
        highscore_points.setText(highscores.get(position).getScore() + " ");
        return view;
    }

}