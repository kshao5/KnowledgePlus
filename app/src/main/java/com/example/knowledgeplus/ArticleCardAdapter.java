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

import java.util.ArrayList;

public class ArticleCardAdapter extends ArrayAdapter<ArticleCard> {
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

        // TODO: Replace example with real image
        switch (articleCard.getId()) {
            case "EXAMPLE#00":
                imageView.setImageResource(R.drawable.knowledge);
                break;
            case "EXAMPLE#01":
                imageView.setImageResource(R.drawable.mirror);
                break;
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Start an "ArticleDetails" activity
                Log.i("ArticleCardAdapter", "START 'ArticleDetails' ACTIVITY");
                Intent intent = new Intent(context, EmptyPublishActivity.class);
                //intent.putExtra("ARTICLE_ID", articleCards.get(position).getId());
                context.startActivity(intent);
            }
        });


        return convertView;
    }
}
