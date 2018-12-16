package com.example.ivode.trivia;

import android.support.annotation.NonNull;

/** Highscore with name and score. Comparable to compare with other highscores and form a ranking. */
public class Highscore implements Comparable<Highscore> {

    private String name, score;

    Highscore(String name, String score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public int compareTo(@NonNull Highscore other) {
        return other.score.compareTo(score);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}