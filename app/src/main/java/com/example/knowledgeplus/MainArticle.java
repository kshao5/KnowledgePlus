package com.example.knowledgeplus;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainArticle extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainarticle);
    }

    public void goToWriteArticle(View view) {
        Intent newIntent = new Intent(this, writeArticle.class);
        startActivity(newIntent);
    }
}
