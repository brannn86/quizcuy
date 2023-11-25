package com.brafly.quizcuy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginRegisterActivity extends AppCompatActivity {

    Button login_btn, register_btn;
    FirebaseAuth mAuth;

    // check if user is already logged in
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Toast.makeText(LoginRegisterActivity.this, "Already logged in.",
                    Toast.LENGTH_SHORT).show();
            Intent toMain = new Intent(LoginRegisterActivity.this, MainActivity.class);
            startActivity(toMain);
            finish();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        login_btn = findViewById(R.id.login_btn);
        register_btn = findViewById(R.id.register_btn);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toLogin = new Intent(LoginRegisterActivity.this, LoginActivity.class);
                startActivity(toLogin);
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toRegister = new Intent(LoginRegisterActivity.this, RegisterActivity.class);
                startActivity(toRegister);
            }
        });


    }
}
