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
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button login_btn, back_btn;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login_btn = findViewById(R.id.login_btn);
        back_btn = findViewById(R.id.back_btn);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toBack = new Intent(LoginActivity.this, LoginRegisterActivity.class);
                startActivity(toBack);
            }
        });
    }

    // login method
    private void loginUser() {
        String user_email, user_password;
        user_email = String.valueOf(email.getText());
        user_password = String.valueOf(password.getText());
        mAuth = FirebaseAuth.getInstance();

        // Checks if all fields is empty
        if (TextUtils.isEmpty(user_email) || TextUtils.isEmpty(user_password)) {
            Toast.makeText(LoginActivity.this, "All fields are required.", Toast.LENGTH_SHORT).show();
            return;
        }

        // login code
        mAuth.signInWithEmailAndPassword(user_email, user_password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Authentication success.",
                                    Toast.LENGTH_SHORT).show();
                            Intent toMain = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(toMain);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Account not found, register first.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
