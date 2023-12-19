package com.brafly.quizcuy.views;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.brafly.quizcuy.R;
import com.brafly.quizcuy.viewmodel.QuestionViewModel;

import org.checkerframework.checker.nullness.qual.NonNull;


public class ResultFragment extends Fragment {

    private NavController navController;
    private QuestionViewModel viewModel;
    private TextView correctAnswered, wrongAnswered, notAnswered;
    private TextView percentTv;
    private ProgressBar scoreProgressbar;
    private String quizId;
    private Button homeBtn;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        correctAnswered = view.findViewById(R.id.correct_num);
        wrongAnswered = view.findViewById(R.id.wrong_num);
        notAnswered = view.findViewById(R.id.notanswered_num);
        percentTv = view.findViewById(R.id.percentage);
        scoreProgressbar = view.findViewById(R.id.percentage_bar);
        homeBtn = view.findViewById(R.id.back_btn);
    }
}