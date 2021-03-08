package com.example.knowledgeplus;

public class Comment {
    String aid;
    String uid;
    String text;

    public Comment(String aid, String uid, String text) {
        this.aid = aid;
        this.uid = uid;
        this.text = text;
    }

    public String getAid() {
        return aid;
    }

    public String getUid() {
        return uid;
    }

    public String getText() {
        return text;
    }
}
