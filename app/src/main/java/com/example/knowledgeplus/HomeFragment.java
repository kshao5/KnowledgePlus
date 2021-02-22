package com.example.knowledgeplus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    final String TAG = "HomeFragment";
    public static final String ARG_MODE = "ARG_MODE";
    ListView listView;
    ArrayList<ArticleCard> articleCards;

    private boolean myArticleOnly;

    public static HomeFragment newInstance(boolean myArticleOnly) {
        Bundle args = new Bundle();
        args.putBoolean(ARG_MODE, myArticleOnly);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myArticleOnly = getArguments().getBoolean(ARG_MODE);
        articleCards = new ArrayList<ArticleCard>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        listView = (ListView) view.findViewById(R.id.listView);

        //TODO: Replace examples with real articles
        articleCards.add(ArticleCard.newExample(0));
        articleCards.add(ArticleCard.newExample(1));
        articleCards.add(ArticleCard.newExample(0));
        articleCards.add(ArticleCard.newExample(1));
        articleCards.add(ArticleCard.newExample(0));
        articleCards.add(ArticleCard.newExample(1));
        articleCards.add(ArticleCard.newExample(0));
        articleCards.add(ArticleCard.newExample(1));

        ArticleCardAdapter articleCardAdapter = new ArticleCardAdapter(getContext(), articleCards);
        listView.setAdapter(articleCardAdapter);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
