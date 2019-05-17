package com.mwx.springboot.entity.maoyan;

public class FilmComment {

    private int id;
    private String filmName;
    private String comment;

    public FilmComment(int id, String filmName, String comment) {
        this.id = id;
        this.filmName = filmName;
        this.comment = comment;
    }

    public FilmComment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
