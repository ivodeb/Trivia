package com.example.ivode.trivia;

/** Highscore with name and score. Comparable to compare with other highscores and form a ranking. */
public class Highscore implements Comparable<Highscore> {

    private String name, score;

    Highscore(String name, String score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public int compareTo(Highscore highscore) {
        return highscore.score.compareTo(score);
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}