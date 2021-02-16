package com.example.knowledgeplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText email, password;
    Button loginButton;
    FirebaseAuth fAuth;
    TextView registerTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        fAuth = FirebaseAuth.getInstance();
        loginButton = findViewById(R.id.login);

        registerTextView = findViewById(R.id.registerTextView);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailString = email.getText().toString().trim();
                String passwordString = password.getText().toString().trim();
                if (TextUtils.isEmpty(passwordString)) {
                    password.setError("Password is Required");
                    return;
                }
                Log.d("emailString", emailString);
                Log.d("password", passwordString);
                fAuth.signInWithEmailAndPassword(emailString, passwordString).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Log.d("password", passwordString);
                            Toast.makeText(Login.this, "Logged in Successfully", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Log.d("password", passwordString);
                            Toast.makeText(Login.this, "Error !" + task.getException(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        registerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });
    }
}