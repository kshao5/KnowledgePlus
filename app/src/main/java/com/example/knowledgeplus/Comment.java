package com.example.knowledgeplus;

public class Comment {
    String id;
    String uid;
    String username;
    String text;
    String date;

    public Comment() {

    }

    public Comment(String id, String uid, String username, String text, String date) {
        this.id = id;
        this.uid = uid;
        this.username = username;
        this.text = text;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    public static Comment newInstance(String id, String uid, String username, String text, String date) {
        return new Comment(id, uid, username, text, date);
    }
}
