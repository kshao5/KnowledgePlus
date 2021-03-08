package com.example.knowledgeplus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

//TODO
// for displaying detail of article
public class articleDetail extends AppCompatActivity {
    //String id;
    //String title;
    //int nViews;
    //int nComments;
    //String author;
    //String uid;
    //String location;
    //String publishDate;
    //String body;
    //String imageURL;
    ArticleCard articleCard;
    TextView tvTitle, tvAuthor, tvLocation, tvPublishDate, tvNViews, tvNComments, tvBody, tvComment;
    EditText editText;
    Button send;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        //TextView articleContent = (TextView)findViewById(R.id.articleBody);

        Intent intent = getIntent();
        //id = intent.getStringExtra(ArticleCardAdapter.ARTICLE_ID, articleCard.getId());
        //title = intent.getStringExtra(ArticleCardAdapter.ARTICLE_TITLE, articleCard.getTitle());
        //nViews = intent.getIntExtra(ArticleCardAdapter.ARTICLE_NVIEWS, articleCard.getnViews());
        //nComments = intent.getIntExtra(ArticleCardAdapter.ARTICLE_NCOMMENTS, articleCard.getnComments());
        //intent.getStringExtra(ArticleCardAdapter.ARTICLE_AUTHOR, articleCard.getAuthor());
        //intent.getStringExtra(ArticleCardAdapter.ARTICLE_UID, articleCard.getUid());
        //intent.getStringExtra(ArticleCardAdapter.ARTICLE_LOCATION, articleCard.getLocation());
        //intent.getStringExtra(ArticleCardAdapter.ARTICLE_PUBLISHDATE, articleCard.getPublishDate());
        //intent.getStringExtra(ArticleCardAdapter.ARTICLE_BODY, articleCard.getBody());
        //intent.getStringExtra(ArticleCardAdapter.ARTICLE_IMAGEURL, articleCard.getImageURL());
        articleCard = (ArticleCard) intent.getSerializableExtra("My Class");

        tvTitle = (TextView) findViewById(R.id.textViewTitle);
        tvAuthor = (TextView) findViewById(R.id.textViewAuthor);
        tvLocation = (TextView) findViewById(R.id.textViewLocation);
        tvPublishDate = (TextView) findViewById(R.id.textViewPublishdate);
        tvNViews = (TextView) findViewById(R.id.textViewNViews);
        tvNComments = (TextView) findViewById(R.id.textViewNComments);
        tvBody = (TextView) findViewById(R.id.textViewBody);
        tvComment = (TextView) findViewById(R.id.textViewComment);
        editText = (EditText) findViewById(R.id.editText);
        send = (Button) findViewById(R.id.button);
        imageView = (ImageView) findViewById(R.id.imageView);

        tvTitle.setText(articleCard.getTitle());
        tvAuthor.setText(articleCard.getAuthor());
        tvLocation.setText(articleCard.getLocation());
        tvPublishDate.setText(articleCard.getPublishDate());
        tvNViews.setText(""+articleCard.getnViews());
        tvNComments.setText(""+articleCard.getnComments());
        tvBody.setText(articleCard.getBody());

        tvBody.setMovementMethod(new ScrollingMovementMethod());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // TODO: set tvComment
        // TODO: set imageView

        // TODO: set send
    }

    /*
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
    */
}