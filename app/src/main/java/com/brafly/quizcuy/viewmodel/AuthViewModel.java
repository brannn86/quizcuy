package com.brafly.quizcuy.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.brafly.quizcuy.repository.Authentication;
import com.google.firebase.auth.FirebaseUser;

public class AuthViewModel extends AndroidViewModel {
    private MutableLiveData<FirebaseUser> userMutableLiveData;
    private FirebaseUser user;
    private Authentication authentication;

    public MutableLiveData<FirebaseUser> getUserMutableLiveData() {
        return userMutableLiveData;
    }

    public FirebaseUser getUser() {
        return user;
    }

    public AuthViewModel(@NonNull Application application) {
        super(application);
        authentication = new Authentication(application);
        user = authentication.getUser();
        userMutableLiveData = authentication.getUserMutableLiveData();
    }

    public void Register(String user_email, String user_password) {
        authentication.Register(user_email, user_password);
    }
    public void Login(String user_email, String user_password) {
        authentication.Login(user_email, user_password);
    }
    public void Logout(){
        authentication.Logout();
    }
}
