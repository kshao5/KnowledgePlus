package com.example.knowledgeplus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MyArticlesActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_articles);

        listView = findViewById(R.id.listView);

        ArrayList<ArticleCard> myArticleCards = new ArrayList<ArticleCard>();

        for (int i = 0; i < 8; i++) {
            myArticleCards.add(ArticleCard.newExample(0));
        }

        MyArticleCardAdapter myArticleCardAdapter = new MyArticleCardAdapter(this, myArticleCards);

        listView.setAdapter(myArticleCardAdapter);
    }
}