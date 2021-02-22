package com.example.knowledgeplus;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CommentCardAdapter extends ArrayAdapter<CommentCard> {
    final String TAG = "CommentCardAdapter";
    Context context;
    public CommentCardAdapter(Context context, ArrayList<CommentCard> commentCards) {
        super(context, 0, commentCards);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        CommentCard commentCard = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.my_comment_card, parent, false);
        }

        TextView comment = (TextView) convertView.findViewById(R.id.comment);
        TextView articleTitle = (TextView) convertView.findViewById(R.id.articleTitle);
        TextView publishDate = (TextView) convertView.findViewById(R.id.publishDate);
        ImageButton delete = (ImageButton) convertView.findViewById(R.id.deleteIcon);

        comment.setText(commentCard.content);
        articleTitle.setText(commentCard.articleTitle);
        publishDate.setText(commentCard.publishDate);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Delete this comment
                Log.i(TAG, "TODO: Delete this comment");
            }
        });


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Start an "ArticleDetails" activity
                Log.i(TAG,"TODO: Start 'ArticleDetails' Activity");
                Intent intent = new Intent(context, articleDetail.class);
                //intent.putExtra("ARTICLE_ID", articleCards.get(position).getId());
                context.startActivity(intent);
            }
        });


        return convertView;
    }

}
