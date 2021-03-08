package com.example.knowledgeplus;

public class ArticleCard {
    String id;
    String title;
    int nViews;
    int nComments;
    String author;
    String uid;
    String location;
    String publishDate;
    String body;
    String imageURL;

    public ArticleCard() {
    }

    public ArticleCard(String id, String title, int nViews, int nComments, String author, String uid, String location, String publishDate, String body, String imageURL) {
        this.id = id;
        this.title = title;
        this.nViews = nViews;
        this.nComments = nComments;
        this.author = author;
        this.uid = uid;
        this.location = location;
        this.publishDate = publishDate;
        this.body = body;
        this.imageURL = imageURL;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getnViews() {
        return nViews;
    }

    public int getnComments() {
        return nComments;
    }

    public String getAuthor() {
        return author;
    }

    public String getUid() {
        return uid;
    }

    public String getLocation() {
        return location;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public String getBody() {
        return body;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setnViews(int nViews) {
        this.nViews = nViews;
    }

    public void setnComments(int nComments) {
        this.nComments = nComments;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public ArticleCard newInstance(String id, String title, int nViews, int nComments, String author, String uid, String location, String publishDate, String body, String imageURL) {
        return new ArticleCard(id, title, nViews, nComments, author, uid, location, publishDate, body, imageURL);
    }
}
