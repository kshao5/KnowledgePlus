package com.example.knowledgeplus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MyCommentsActivity extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_comments);

        listView = findViewById(R.id.listView);

        ArrayList<CommentCard> commentCards = new ArrayList<CommentCard>();

        commentCards.add(CommentCard.newExample(0));
        commentCards.add(CommentCard.newExample(1));

        CommentCardAdapter commentCardAdapter = new CommentCardAdapter(this, commentCards);

        listView.setAdapter(commentCardAdapter);
    }
}