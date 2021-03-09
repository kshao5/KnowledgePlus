package com.example.knowledgeplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.knowledgeplus.SendNotificationPack.APIService;
import com.example.knowledgeplus.SendNotificationPack.Client;
import com.example.knowledgeplus.SendNotificationPack.Data;
import com.example.knowledgeplus.SendNotificationPack.MyResponse;
import com.example.knowledgeplus.SendNotificationPack.NotificationSender;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//TODO
// for displaying detail of article
public class articleDetail extends AppCompatActivity {
    private final String TAG = "ArticleDetail";
    ArticleCard articleCard;
    TextView tvTitle, tvAuthor, tvLocation, tvPublishDate, tvNViews, tvNComments, tvBody, tvComment;
    EditText editText;
    Button send;
    LinearLayout images;
    DatabaseReference db;
    StorageReference imageReference;
    LinearLayout.LayoutParams lp;

    private APIService apiService;

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
        images = (LinearLayout) findViewById(R.id.images);
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,500);
        lp.setMargins(0,10,0,10);
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);

        tvTitle.setText(articleCard.getTitle());
        tvAuthor.setText(articleCard.getAuthor());
        if (articleCard.getLocation() == null || articleCard.getLocation().isEmpty()) {
            tvLocation.setText("Unknown");
        } else {
            tvLocation.setText(articleCard.getLocation());
        }
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
                    commentString += "\n" + comment.getUsername() +  ", " + comment.getDate() + ":\n" + comment.getText() + "\n";
                }
                tvComment.setText(commentString);
                int nComments = (int)snapshot.getChildrenCount();
                tvNComments.setText(""+nComments);
                FirebaseDatabase.getInstance().getReference("article").child(articleCard.getId()).child("nComments").setValue(nComments);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Nothing
            }
        });

        // set imageView
        if (articleCard.nImages == 0) {
            Log.i(TAG, "Article " + articleCard.getTitle() +" has no image");
        } else {
            loadImages();
        }

        // set send button
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(articleDetail.this);
                builder.setMessage("Send the comment?")
                        .setNegativeButton("CANCEL", null)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                sendComment();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void sendComment() {
        String text = editText.getText().toString().trim();
        String username = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (text.isEmpty()) {
            Toast.makeText(articleDetail.this, "Please enter your comment", Toast.LENGTH_SHORT).show();
            return;
        }

        String comment_id = db.push().getKey();
        android.text.format.DateFormat df = new android.text.format.DateFormat();
        String date = df.format("yyyy/MM/dd", Calendar.getInstance().getTime()).toString();
        db.child(comment_id).setValue(Comment.newInstance(comment_id, uid, username, text, date));

        Toast.makeText(articleDetail.this, "Comment sent", Toast.LENGTH_SHORT).show();
        editText.setText("");
        sendNotification(username);
    }

    // send notification to author of the article
    private void sendNotification(String commenter) {
        FirebaseDatabase.getInstance().getReference().child("Tokens").child(articleCard.getUid()).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String usertoken = snapshot.getValue(String.class);
                String title = "You got a new comment for your knowledge!";
                Log.d("158", title);
                String body = commenter + " comments on your article";
                Log.d("160", body);
                sendNotification(usertoken, title, body);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sendNotification(String usertoken, String title, String message) {
        Data data = new Data(title, message);
        NotificationSender sender = new NotificationSender(data, usertoken);
        apiService.sendNotification(sender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (response.code() == 200) {
                    Log.d("190", "success");
                    if (response.body().success != 1) {
                        Toast.makeText(articleDetail.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {

            }
        });

    }

    private void loadImages() {
        imageReference = FirebaseStorage.getInstance().getReference().child("images").child(articleCard.getId());

        for (int i = 0; i < articleCard.getnImages(); i++) {
            imageReference.child(""+i).getBytes(1024*1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    ImageView imageView = new ImageView(getApplicationContext());
                    imageView.setImageBitmap(bitmap);
                    imageView.setLayoutParams(lp);
                    images.addView(imageView);
                }
            });
        }
    }


}