package com.example.knowledgeplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

//TODO
// for displaying detail of article
public class articleDetail extends AppCompatActivity {
    ArticleCard articleCard;
    TextView tvTitle, tvAuthor, tvLocation, tvPublishDate, tvNViews, tvNComments, tvBody, tvComment;
    EditText editText;
    Button send;
    ImageView imageView;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        Intent intent = getIntent();
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

        db = FirebaseDatabase.getInstance().getReference("comment").child(articleCard.getId());
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String commentString = "";
                for (DataSnapshot commentSnapshot : snapshot.getChildren()) {
                    Comment comment = commentSnapshot.getValue(Comment.class);
                    commentString += "\n\n" + comment.getUsername() +  ", " + comment.getDate() + ":\n" + comment.getText();
                }
                tvComment.setText(commentString);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Nothing
            }
        });
        // TODO: set imageView

        // TODO: set send
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: show dialog

                String text = editText.getText().toString().trim();
                String username = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                if (text.isEmpty()) {
                    Toast.makeText(articleDetail.this, "Please enter your comment", Toast.LENGTH_SHORT).show();
                }


                String comment_id = db.push().getKey();
                db.child(comment_id).setValue(Comment.newInstance(comment_id, uid, username, text, Calendar.getInstance().getTime().toString()));

                Toast.makeText(articleDetail.this, "Comment sent", Toast.LENGTH_SHORT).show();
                editText.setText("");
            }
        });
    }


}