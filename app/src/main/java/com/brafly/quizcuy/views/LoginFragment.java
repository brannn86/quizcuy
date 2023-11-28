package com.brafly.quizcuy.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.brafly.quizcuy.R;
import com.brafly.quizcuy.viewmodel.AuthViewModel;
import com.google.firebase.auth.FirebaseUser;

public class LoginFragment extends Fragment {
    private AuthViewModel viewModel;
    private NavController navController;
    EditText email, password;
    TextView toRegister;
    Button login_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        login_btn = view.findViewById(R.id.login_btn);
        toRegister = view.findViewById(R.id.toRegister);

        toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_email, user_password;
                user_email = String.valueOf(email.getText());
                user_password = String.valueOf(password.getText());
                if (!user_email.isEmpty() && !user_password.isEmpty()){
                    viewModel.Login(user_email, user_password);
                    Toast.makeText(getContext(), "Berhasil Login!",
                            Toast.LENGTH_SHORT).show();
                    viewModel.getUserMutableLiveData().observe(getViewLifecycleOwner(), new Observer<FirebaseUser>() {
                        @Override
                        public void onChanged(FirebaseUser user) {
                            if (user != null) {
                                navController.navigate(R.id.action_loginFragment_to_listFragment);
                            }
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Masukkan Email dan Password!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(AuthViewModel.class);
    }
}