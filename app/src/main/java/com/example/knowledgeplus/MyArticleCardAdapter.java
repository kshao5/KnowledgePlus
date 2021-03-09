package com.example.knowledgeplus;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MyArticleCardAdapter extends ArrayAdapter<ArticleCard> {
    final String TAG = "MyArticleCardAdapter";
    Context context;
    ImageView imageView;
    TextView title, nViews_nComments, publishDate;
    ImageButton edit, delete;
    ArticleCard articleCard;

    public MyArticleCardAdapter(Context context, ArrayList<ArticleCard> articleCards) {
        super(context, 0, articleCards);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        articleCard = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.my_article_card, parent, false);
        }

        imageView = (ImageView) convertView.findViewById(R.id.imageView);
        title = (TextView) convertView.findViewById(R.id.title);
        nViews_nComments = (TextView) convertView.findViewById(R.id.nViews_nComments);
        publishDate = (TextView) convertView.findViewById(R.id.publishDate);
        edit = (ImageButton) convertView.findViewById(R.id.editIcon);
        delete = (ImageButton) convertView.findViewById(R.id.deleteIcon);

        title.setText(articleCard.title);
        nViews_nComments.setText(articleCard.nViews+" views, "+articleCard.nComments+" comments");
        publishDate.setText(articleCard.publishDate);

        if (articleCard.nImages == 0) {
            imageView.setImageResource(R.drawable.knowledge);
        } else {
            StorageReference imageReference = FirebaseStorage.getInstance().getReference().child("images").child(articleCard.getId()).child("0");
            imageReference.getBytes(1024*1024)
                    .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            imageView.setImageBitmap(bitmap);
                        }
                    });
        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Edit this article?")
                        .setNegativeButton("CANCEL", null)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                editArticle();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Edit this article?")
                        .setNegativeButton("CANCEL", null)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteArticle();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, articleDetail.class);
                intent.putExtra("My Class", articleCard);
                Log.i(TAG, "Start Article Details");
                context.startActivity(intent);
            }
        });


        return convertView;
    }

    private void editArticle() {
        //TODO
    }

    private void deleteArticle() {
        FirebaseDatabase.getInstance().getReference("comment").child(articleCard.getId()).removeValue();
        FirebaseDatabase.getInstance().getReference("article").child(articleCard.getId()).removeValue();
        for (int i = 0; i < articleCard.getnImages(); i++) {
            FirebaseStorage.getInstance().getReference("images").child(articleCard.getId()).child(""+i).delete();
        }

    }
}
