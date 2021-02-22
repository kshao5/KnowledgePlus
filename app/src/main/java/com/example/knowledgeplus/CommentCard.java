package com.example.knowledgeplus;

public class CommentCard {
    public String cid;
    public String content;

    public String aid;
    public String articleTitle;

    public String uid;
    public String username;

    public String publishDate;

    public CommentCard(String cid, String content,
                       String aid, String articleTitle,
                       String uid, String username,
                       String publishDate) {
        this.cid = cid;
        this.content = content;
        this.aid = aid;
        this.articleTitle = articleTitle;
        this.uid = uid;
        this.username = username;
        this.publishDate = publishDate;
    }

    public CommentCard() {
        this.cid = "EMPTY";
        this.content = "";
        this.aid = "";
        this.articleTitle = "";
        this.uid = "";
        this.username = "";
        this.publishDate = "";
    }

    public static CommentCard newExample(int exampleNo) {
        switch (exampleNo) {
            case 0:
                return new CommentCard("EXAMPLE#0000", "A very useful article!",
                                        "EXAMPLE#0", "How many vegetables are needed?",
                                        "ADMIN#00", "Taipeng Liu",
                                        "02/21/2021");
            case 1:
                return new CommentCard("EXAMPLE#0001", "Can I share this article?",
                        "EXAMPLE#1", "The future of mobile app development",
                        "ADMIN#00", "Taipeng Liu",
                        "02/21/2021");
        }

        return new CommentCard();
    }
}