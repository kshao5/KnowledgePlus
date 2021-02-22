package com.example.knowledgeplus;

public class ArticleCard {
    public String id;
    public String title;
    public int nViews;
    public int nComments;
    public String author;
    public String location;
    public String publishDate;

    public ArticleCard() {
        this.id = "EMPTY";
        this.title = "";
        this.nViews = 0;
        this.nComments = 0;
        this.author = "";
        this.location = "";
        this.publishDate = "";
    }

    public ArticleCard(String id, String title, int nViews, int nComments,
                       String author, String location, String publishDate) {
        this.id = id;
        this.title = title;
        this.nViews = nViews;
        this.nComments = nComments;
        this.author = author;
        this.location = location;
        this.publishDate = publishDate;
    }

    public String getId() {
        return this.id;
    }

    public static ArticleCard newExample(int exampleNo) {
        switch (exampleNo) {
            case 0:
                return new ArticleCard("EXAMPLE#00",
                        "How many vegetables are needed?",
                        10, 2,
                        "Taipeng Liu",
                        "Santa Clara, CA",
                        "02/21/2021");
            case 1:
                return new ArticleCard("EXAMPLE#01",
                        "The future of mobile app development",
                        5, 0,
                        "Kedong Shao",
                        "Santa Clara, CA",
                        "02/21/2021");
        }
        return new ArticleCard();
    }


}
