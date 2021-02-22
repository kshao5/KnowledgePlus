package com.example.knowledgeplus;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AccountFragment extends Fragment  {
    public static final String ARG_USERNAME = "ARG_USERNAME";
    private String username;

    public static AccountFragment newInstance(String username) {
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, username);
        AccountFragment fragment = new AccountFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = getArguments().getString(ARG_USERNAME);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        Button profile = (Button) view.findViewById(R.id.profileButton);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        Button myArticles = (Button) view.findViewById(R.id.myArticlesButton);
        myArticles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyArticlesActivity.class);
                startActivity(intent);
            }
        });

        Button myComments = (Button) view.findViewById(R.id.myCommentsButton);
        myComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyCommentsActivity.class);
                startActivity(intent);
            }
        });

        Button about = (Button) view.findViewById(R.id.aboutButton);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
            }
        });

        Button logout = (Button) view.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                Toast.makeText(getContext(), "You have successfully logged out", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
        return view;
    }

}
