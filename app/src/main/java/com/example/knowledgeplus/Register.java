package com.example.knowledgeplus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class Register extends AppCompatActivity {
    EditText fullName, email, password;
    Button registerButton;
    FirebaseAuth fAuth;
    TextView loginText;

    //TODO: Remove
    //Button testHomeActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Sign Up");

        fullName = findViewById(R.id.fullName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        registerButton = findViewById(R.id.registerButton);
        loginText = findViewById(R.id.loginText);


        fAuth = FirebaseAuth.getInstance();

        // user has already loggedin
        if(fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passwordString = password.getText().toString().trim();
                String emailString = email.getText().toString().trim();
                if (TextUtils.isEmpty(passwordString)) {
                    password.setError("Password is Required");
                    return;
                }

                // register the user in firebase
                fAuth.createUserWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(Register.this, "User Created.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(Register.this, "Error !" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        loginText.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });



        //TODO: Remove
        /*
        testHomeActivity = (Button) findViewById(R.id.testHomeActivity);
        testHomeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toHomeIntent = new Intent(Register.this, HomeActivity.class);
                startActivity(toHomeIntent);
            }
        });
        */
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}