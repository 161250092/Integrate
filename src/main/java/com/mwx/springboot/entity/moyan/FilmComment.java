package com.mwx.springboot.entity.moyan;

public class FilmComment {

    private String id;
    private String filmName;
    private String comment;

    public FilmComment(String id, String filmName, String comment) {
        this.id = id;
        this.filmName = filmName;
        this.comment = comment;
    }

    public FilmComment() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
