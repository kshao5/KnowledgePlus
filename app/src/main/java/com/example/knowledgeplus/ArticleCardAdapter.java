package com.example.knowledgeplus;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class ArticleCardAdapter extends ArrayAdapter<ArticleCard> {
    private static final String TAG = "ArticleCardAdapter";
    private final int MAX_DOWNLOAD_BUFFER_SIZE = 1024*1024*10; //10MB
    Context context;

    public ArticleCardAdapter(Context context, ArrayList<ArticleCard> articleCards) {
        super(context, 0, articleCards);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ArticleCard articleCard = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.article_card, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView nViews_nComments = (TextView) convertView.findViewById(R.id.nViews_nComments);
        TextView author = (TextView) convertView.findViewById(R.id.author);
        TextView publishDate = (TextView) convertView.findViewById(R.id.publishDate);

        title.setText(articleCard.title);
        nViews_nComments.setText(articleCard.nViews+" views, "+articleCard.nComments+" comments");
        author.setText(articleCard.author);
        publishDate.setText(articleCard.publishDate);

        Log.i(TAG, "Article " + articleCard.getTitle() + ", has " + articleCard.getnImages() + " images");
        if (articleCard.nImages == 0) {
            imageView.setImageResource(R.drawable.knowledge);
        } else {
            StorageReference imageReference = FirebaseStorage.getInstance().getReference().child("images").child(articleCard.getId()).child("0");
            imageReference.getBytes(MAX_DOWNLOAD_BUFFER_SIZE)
                    .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            imageView.setImageBitmap(bitmap);
                        }
                    });
        }


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                articleCard.setnViews(articleCard.getnViews()+1);
                Intent intent = new Intent(context, articleDetail.class);
                intent.putExtra("My Class", articleCard);
                Log.i(TAG, "Start Article Details");
                context.startActivity(intent);
                FirebaseDatabase.getInstance().getReference("article").child(articleCard.getId()).child("nViews").setValue(articleCard.getnViews());
            }
        });

        return convertView;
    }
}
