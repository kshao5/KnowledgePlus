package com.example.knowledgeplus;

import java.io.Serializable;

public class Article implements Serializable {
    private String title;
    private String body;
    private String location;
    private String author;
    public Article(String title, String body, String location, String author) {
        this.title = title;
        this.body = body;
        this.location = location;
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getLocation() {
        return location;
    }
}
