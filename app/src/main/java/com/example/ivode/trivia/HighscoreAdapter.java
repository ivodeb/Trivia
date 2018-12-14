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


public class HighscoreAdapter extends ArrayAdapter<Highscore> {

    private ArrayList<Highscore> highscores;

    public HighscoreAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Highscore> objects) {
        super(context, resource, objects);
        this.highscores = objects;

        Collections.sort(highscores);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.highscore, parent,
                    false);
        }

        Highscore highscore = highscores.get(position);

        TextView highscore_name = convertView.findViewById(R.id.highscore_name);
        TextView highscore_points = convertView.findViewById(R.id.highscore_points);

        highscore_name.setText(highscore.getName() + " ");
        highscore_points.setText(highscore.getScore() + " ");

        return convertView;
    }

}