package com.mwx.springboot.entity.integrate;

public class Comment {

    private String movie_name;
    private String comment;

    public Comment() {
    }

    public Comment(String movie_name, String comment) {
        this.movie_name = movie_name;
        this.comment = comment;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
