package com.example.knowledgeplus;

import java.io.Serializable;

public class Article implements Serializable {
    private String title;
    private String body;
    private String location;
    private String username;
    private String uid;
    private String date;
    private int nViews;
    private int nComments;
    public Article() {

    }

    public Article(String title, String body, String location, String username, String uid, String date, int nViews, int nComments) {
        this.title = title;
        this.body = body;
        this.location = location;
        this.username = username;
        this.uid = uid;
        this.date = date;
        this.nViews = nViews;
        this.nComments = nComments;
    }

    public String getUid() {
        return uid;
    }

    public String getTitle() {
        return title;
    }

    public String getUsername() {return username;}

    public String getBody() {
        return body;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {return date;}

    public int getnViews() {
        return nViews;
    }

    public int getnComments() {
        return nComments;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setnViews(int nViews) {
        this.nViews = nViews;
    }

    public void setnComments(int nComments) {
        this.nComments = nComments;
    }

    public Article deepCopy(Article oldArticle) {
        Article newArticle = new Article(oldArticle.getTitle(),
                                         oldArticle.getBody(),
                                         oldArticle.getLocation(),
                                         oldArticle.getUsername(),
                                         oldArticle.getUid(),
                                         oldArticle.getDate(),
                                         oldArticle.getnViews(),
                                         oldArticle.getnComments());

        return newArticle;
    }
}
