package com.mwx.springboot.entity.douban;

public class Hotcomment {

    private int hotcomments_id;
    private int movie_id;
    private String hotCommentAuthor;
    private String hotCommentDate;
    private String hotCommentContent;

    public Hotcomment(){}

    public Hotcomment(int hotcomments_id, int movie_id, String hotCommentAuthor, String hotCommentDate, String hotCommentContent) {
        this.hotcomments_id = hotcomments_id;
        this.movie_id = movie_id;
        this.hotCommentAuthor = hotCommentAuthor;
        this.hotCommentDate = hotCommentDate;
        this.hotCommentContent = hotCommentContent;
    }

    public String getHotCommentAuthor() {
        return hotCommentAuthor;
    }

    public void setHotCommentAuthor(String hotCommentAuthor) {
        this.hotCommentAuthor = hotCommentAuthor;
    }

    public String getHotCommentDate() {
        return hotCommentDate;
    }

    public void setHotCommentDate(String hotCommentDate) {
        this.hotCommentDate = hotCommentDate;
    }

    public String getHotCommentContent() {
        return hotCommentContent;
    }

    public void setHotCommentContent(String hotCommentContent) {
        this.hotCommentContent = hotCommentContent;
    }

    public int getHotcomments_id() {
        return hotcomments_id;
    }

    public void setHotcomments_id(int hotcomments_id) {
        this.hotcomments_id = hotcomments_id;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }
}
