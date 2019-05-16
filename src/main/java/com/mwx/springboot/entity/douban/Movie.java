package com.mwx.springboot.entity.douban;


import java.io.Serializable;
import java.util.List;

public class Movie implements Serializable{

    private int id;
    private String title;
    private String directors;
    private String rate;
    private String casts;
    private String type;
    private String nation;
    private String language;
    private String date;
    private String time;
    private String peopleNumber;
    private String introduction;

    private List<Hotcomment> hotcommentList;

    public Movie() {
    }

    public Movie(int id, String title, String directors, String rate, String casts, String type, String nation, String language, String date, String time, String peopleNumber, String introduction) {
        this.id = id;
        this.title = title;
        this.directors = directors;
        this.rate = rate;
        this.casts = casts;
        this.type = type;
        this.nation = nation;
        this.language = language;
        this.date = date;
        this.time = time;
        this.peopleNumber = peopleNumber;
        this.introduction = introduction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirectors() {
        return directors;
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getCasts() {
        return casts;
    }

    public void setCasts(String casts) {
        this.casts = casts;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(String peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }


    public List<Hotcomment> getHotcommentList() {
        return hotcommentList;
    }

    public void setHotcommentList(List<Hotcomment> hotcommentList) {
        this.hotcommentList = hotcommentList;
    }
}
