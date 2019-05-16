package com.mwx.springboot.entity.moyan;

import java.io.Serializable;
import java.util.List;

public class Film implements Serializable {

    private String id;
    private String name;
    private String director;
    private String type;
    private String filmingLocation;
    private String duration;
    private String releasedTime;
    private String score;
    private String description;
    private List<FilmComment> filmCommentList;

    public Film() {
    }

    public Film(String id, String name, String director, String type, String filmingLocation, String duration, String releasedTime, String score, String description, List<FilmComment> filmCommentList) {
        this.id = id;
        this.name = name;
        this.director = director;
        this.type = type;
        this.filmingLocation = filmingLocation;
        this.duration = duration;
        this.releasedTime = releasedTime;
        this.score = score;
        this.description = description;
        this.filmCommentList = filmCommentList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFilmingLocation() {
        return filmingLocation;
    }

    public void setFilmingLocation(String filmingLocation) {
        this.filmingLocation = filmingLocation;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getReleasedTime() {
        return releasedTime;
    }

    public void setReleasedTime(String releasedTime) {
        this.releasedTime = releasedTime;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<FilmComment> getFilmCommentList() {
        return filmCommentList;
    }

    public void setFilmCommentList(List<FilmComment> filmCommentList) {
        this.filmCommentList = filmCommentList;
    }
}
