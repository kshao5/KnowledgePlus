package com.example.knowledgeplus;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;

public class MyArticleCardAdapter extends ArrayAdapter<ArticleCard> {
    final String TAG = "MyArticleCardAdapter";
    Context context;
    public MyArticleCardAdapter(Context context, ArrayList<ArticleCard> articleCards) {
        super(context, 0, articleCards);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ArticleCard articleCard = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.my_article_card, parent, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView nViews_nComments = (TextView) convertView.findViewById(R.id.nViews_nComments);
        TextView publishDate = (TextView) convertView.findViewById(R.id.publishDate);
        ImageButton edit = (ImageButton) convertView.findViewById(R.id.editIcon);
        ImageButton delete = (ImageButton) convertView.findViewById(R.id.deleteIcon);

        title.setText(articleCard.title);
        nViews_nComments.setText(articleCard.nViews+" views, "+articleCard.nComments+" comments");
        publishDate.setText(articleCard.publishDate);

        // TODO: Replace example with real image
        switch (articleCard.getId()) {
            case "EXAMPLE#00":
                imageView.setImageResource(R.drawable.knowledge);
                break;
            case "EXAMPLE#01":
                imageView.setImageResource(R.drawable.mirror);
                break;
        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Start "edit" activity
                Intent myIntent = new Intent(v.getContext(), writeArticle.class);
                myIntent.putExtra("title", title.getText());
                myIntent.putExtra("body", "This is dummy");
                context.startActivity(myIntent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Remove the article
                Log.i(TAG, "TODO: Remove the article");
            }
        });


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Start an "ArticleDetails" activity
                Log.i(TAG, "TODO: Start '''ArticleDetails''' activity");
                Intent intent = new Intent(context, articleDetail.class);
                //intent.putExtra("ARTICLE_ID", articleCards.get(position).getId());
                context.startActivity(intent);
            }
        });


        return convertView;
    }
}
