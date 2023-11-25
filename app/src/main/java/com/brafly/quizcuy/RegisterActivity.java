package com.brafly.quizcuy;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    EditText username, email, password;
    Button register_btn, back_btn;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register_btn = findViewById(R.id.register_btn);
        back_btn = findViewById(R.id.back_btn);

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toBack = new Intent(RegisterActivity.this, LoginRegisterActivity.class);
                startActivity(toBack);
            }
        });

    }

    //Register user method
    private void registerUser() {
        String user_username, user_email, user_password;
        user_username = String.valueOf(username.getText());
        user_email = String.valueOf(email.getText());
        user_password = String.valueOf(password.getText());
        mAuth = FirebaseAuth.getInstance();

        // Checks if all fields is empty or not
        if (TextUtils.isEmpty(user_username) || TextUtils.isEmpty(user_email) || TextUtils.isEmpty(user_password)) {
            Toast.makeText(RegisterActivity.this, "All fields are required.", Toast.LENGTH_SHORT).show();
            return;
        } if (user_password.length() < 6) {
            Toast.makeText(RegisterActivity.this, "Password must be 6 character or more.", Toast.LENGTH_SHORT).show();
            return;
        }

        // send username string to MainActivity
        Intent sendUsername = new Intent(RegisterActivity.this, MainActivity.class);
        sendUsername.putExtra("USERNAME", user_username);

        // actual code for registering user
        mAuth.createUserWithEmailAndPassword(user_email, user_password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Authentication success.",
                                    Toast.LENGTH_SHORT).show();
                            Intent toMain = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(toMain);
                        } else {
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

}
