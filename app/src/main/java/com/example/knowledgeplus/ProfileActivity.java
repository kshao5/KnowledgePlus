package com.example.knowledgeplus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {
    TextView username, email;
    Button changePassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        username = (TextView) findViewById(R.id.username);
        email = (TextView) findViewById(R.id.email);
        changePassword = (Button) findViewById(R.id.changePasswordButton);

        username.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        // TODO : change password functionality
    }
}