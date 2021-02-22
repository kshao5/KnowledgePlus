package com.example.knowledgeplus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class writeArticle extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writearticle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String title = getIntent().getStringExtra("title");
        String body = getIntent().getStringExtra("body");
        EditText titleBar = (EditText) findViewById(R.id.title);
        EditText bodyBar = (EditText) findViewById(R.id.body);
        if (title != null && title.length() > 0) {
            titleBar.setText(title);
        }
        if (body != null && body.length() > 0) {
            bodyBar.setText(body);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToArticleDetail(View view) {
        Intent newIntent = new Intent(this, articleDetail.class);
        startActivity(newIntent);
    }
}
