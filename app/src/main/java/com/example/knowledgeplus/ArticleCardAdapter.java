package com.example.knowledgeplus;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ArticleCardAdapter extends ArrayAdapter<ArticleCard> {
    private static final String TAG = "ArticleCardAdapter";
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

        // TODO: set image articleCard.imageURL
        imageView.setImageResource(R.drawable.mirror);

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
