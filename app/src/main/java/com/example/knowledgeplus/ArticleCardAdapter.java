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
    public static final String ARTICLE_ID = "article_id";
    public static final String ARTICLE_TITLE = "article_title";
    public static final String ARTICLE_NVIEWS = "article_nviews";
    public static final String ARTICLE_NCOMMENTS = "article_ncomments";
    public static final String ARTICLE_AUTHOR = "article_author";
    public static final String ARTICLE_UID = "article_UID";
    public static final String ARTICLE_LOCATION = "article_location";
    public static final String ARTICLE_PUBLISHDATE = "article_publishdate";
    public static final String ARTICLE_BODY = "article_body";
    public static final String ARTICLE_IMAGEURL = "article_iamgeURL";
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
                Intent intent = new Intent(context, articleDetail.class);
                //intent.putExtra(ARTICLE_ID, articleCard.getId());
                //intent.putExtra(ARTICLE_TITLE, articleCard.getTitle());
                //intent.putExtra(ARTICLE_NVIEWS, articleCard.getnViews());
                //intent.putExtra(ARTICLE_NCOMMENTS, articleCard.getnComments());
                //intent.putExtra(ARTICLE_AUTHOR, articleCard.getAuthor());
                //intent.putExtra(ARTICLE_UID, articleCard.getUid());
                //intent.putExtra(ARTICLE_LOCATION, articleCard.getLocation());
                //intent.putExtra(ARTICLE_PUBLISHDATE, articleCard.getPublishDate());
                //intent.putExtra(ARTICLE_BODY, articleCard.getBody());
                //intent.putExtra(ARTICLE_IMAGEURL, articleCard.getImageURL());
                intent.putExtra("My Class", articleCard);
                Log.i(TAG, "Start Article Details");
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
