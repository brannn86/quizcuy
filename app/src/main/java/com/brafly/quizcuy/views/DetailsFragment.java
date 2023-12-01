package com.brafly.quizcuy.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.brafly.quizcuy.R;
import com.brafly.quizcuy.model.QuizListModel;
import com.brafly.quizcuy.viewmodel.QuizListViewModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class DetailsFragment extends Fragment {

    private TextView titleDetail, diff_num, questions_num;
    private Button start_btn;
    private NavController navController;
    private int position;
    private ProgressBar progressBar;
    private QuizListViewModel viewModel;
    private ImageView imgDetail;
    private String quizID;
    private long totalQueCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(QuizListViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        titleDetail = view.findViewById(R.id.titleDetail);
        diff_num = view.findViewById(R.id.diff_num);
        questions_num = view.findViewById(R.id.questions_num);
        start_btn = view.findViewById(R.id.start_btn);
        progressBar = view.findViewById(R.id.detailBar);
        imgDetail = view.findViewById(R.id.imgDetail);

        navController = Navigation.findNavController(view);

        position = DetailsFragmentArgs.fromBundle(getArguments()).getPosition();

        viewModel.getQuizListLiveData().observe(getViewLifecycleOwner(), new Observer<List<QuizListModel>>() {
            @Override
            public void onChanged(List<QuizListModel> quizListModels) {
                QuizListModel quiz = quizListModels.get(position);
                diff_num.setText(String.valueOf(quiz.getDifficulty()));
                titleDetail.setText(quiz.getTitle());
                questions_num.setText(String.valueOf(quiz.getQuestions()));
                Glide.with(view).load(quiz.getImage()).into(imgDetail);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                    }
                }, 2000);
                totalQueCount = quiz.getQuestions();
                quizID = quiz.getQuizId();
            }
        });

        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailsFragmentDirections.ActionDetailsFragmentToQuizFragment action =
                        DetailsFragmentDirections.actionDetailsFragmentToQuizFragment();

                action.setQuizID(quizID);
                action.setTotalQueCount(totalQueCount);
                navController.navigate(action);

            }
        });
    }
}