package com.brafly.quizcuy.repository;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Authentication {
    private Application application;
    private MutableLiveData<FirebaseUser> userMutableLiveData;
    private FirebaseAuth mAuth;

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public FirebaseUser getUser() {
        return mAuth.getCurrentUser();
    }

    public Authentication(Application application) {
        this.application = application;
        userMutableLiveData = new MutableLiveData<>();
        mAuth = FirebaseAuth.getInstance();
    }

    public void Register(String user_email, String user_password) {
        mAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userMutableLiveData.postValue(mAuth.getCurrentUser());
                            Toast.makeText(application, "Authentication success.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(application, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void Login(String user_email, String user_password) {
        mAuth.signInWithEmailAndPassword(user_email, user_password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userMutableLiveData.postValue(mAuth.getCurrentUser());
                            Toast.makeText(application, "Authentication success.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(application, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void Logout() {
        mAuth.signOut();
    }
}
