package com.example.knowledgeplus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

// for displaying detail of article
public class articleDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        TextView articleContent = (TextView)findViewById(R.id.articleContent);
        articleContent.setMovementMethod(new ScrollingMovementMethod());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent newIntent = new Intent(this, HomeActivity.class);
                startActivity(newIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}