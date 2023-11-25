package com.brafly.quizcuy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;
    TextView username_text, email_text;
    Button logout_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        username_text = findViewById(R.id.username);
        email_text = findViewById(R.id.email);
        logout_btn = findViewById(R.id.logout_btn);

        if (user == null) {
            Intent toLoginRegister = new Intent(MainActivity.this, LoginRegisterActivity.class);
            startActivity(toLoginRegister);
            finish();
        } else {
            Intent sendUsername = getIntent();
            String username_string = sendUsername.getStringExtra("USERNAME");

            username_text.setText(username_string);
            email_text.setText(user.getEmail());
        }

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent toLoginRegister = new Intent(MainActivity.this, LoginRegisterActivity.class);
                startActivity(toLoginRegister);
                finish();
            }
        });

    }
}