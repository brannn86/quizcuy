package com.brafly.quizcuy.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.brafly.quizcuy.R;
import com.brafly.quizcuy.model.QuestionModel;
import com.brafly.quizcuy.viewmodel.QuestionViewModel;
import com.brafly.quizcuy.viewmodel.QuizListViewModel;

import java.util.HashMap;
import java.util.List;

public class QuizFragment extends Fragment implements View.OnClickListener {
    private QuestionViewModel viewModel;
    private NavController navController;
    private ProgressBar progressBar;
    private Button option_a, option_b, option_c, next_btn, close_btn;
    private TextView questionTv, ansFeedbackTv, questionNumTv, timerTv;
    private String quizId;
    private long totalQuestions;
    private int currentQueNo = 0;
    private boolean canAnswer = false;
    private long timer;
    private CountDownTimer countDownTimer;
    private int notAnswered = 0;
    private int correctAnswered = 0;
    private int wrongAnswered = 0;
    private  String answer = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getActivity().getApplication())).get(QuestionViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        close_btn = view.findViewById(R.id.close_btn);
        next_btn = view.findViewById(R.id.next_btn);
        option_a = view.findViewById(R.id.option_a);
        option_b = view.findViewById(R.id.option_b);
        option_c = view.findViewById(R.id.option_c);

        progressBar = view.findViewById(R.id.timer_bar);

        questionTv = view.findViewById(R.id.question);
        ansFeedbackTv = view.findViewById(R.id.verify);
        questionNumTv = view.findViewById(R.id.questions_num);
        timerTv = view.findViewById(R.id.timer);

        quizId = QuizFragmentArgs.fromBundle(getArguments()).getQuizID();
        totalQuestions = QuizFragmentArgs.fromBundle(getArguments()).getTotalQueCount();
        viewModel.setQuizId(quizId);

        option_a.setOnClickListener(this);
        option_b.setOnClickListener(this);
        option_c.setOnClickListener(this);
        next_btn.setOnClickListener(this);

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_quizFragment_to_listFragment);
            }
        });

        loadData();
    }

    private void loadData() {
        enableOptions();
        loadQuestions(1);
    }

    private void enableOptions() {
        option_a.setVisibility(View.VISIBLE);
        option_b.setVisibility(View.VISIBLE);
        option_c.setVisibility(View.VISIBLE);

        // enable btn, hide feedbacktv, hide nextquiz btn

        option_a.setEnabled(true);
        option_b.setEnabled(true);
        option_c.setEnabled(true);

        ansFeedbackTv.setVisibility(View.VISIBLE);
        next_btn.setVisibility(View.VISIBLE);



    }

    private void loadQuestions(int i) {

        currentQueNo = i;
        viewModel.getQuestionMutableLiveData().observe(getViewLifecycleOwner(), new Observer<List<QuestionModel>>() {
            @Override
            public void onChanged(List<QuestionModel> questionModels) {
                questionTv.setText(questionModels.get(i - 1).getQuestion());
                option_a.setText(questionModels.get(i - 1).getOption_a());
                option_b.setText(questionModels.get(i - 1).getOption_b());
                option_c.setText(questionModels.get(i - 1).getOption_c());
                timer = questionModels.get(i - 1).getTimer();
                answer = questionModels.get(i-1).getAnswer();
            }
        });

        startTimer();
        canAnswer = true;
    }

    private void startTimer() {
        timerTv.setText(String.valueOf(timer));
        progressBar.setVisibility(View.VISIBLE);
        countDownTimer = new CountDownTimer(timer * 1000, 1000) {
            @Override
            public void onTick(long l) {
                // update time
                timerTv.setText(l / 1000 + "");

                Long percent = l / (timer * 10);
                progressBar.setProgress(percent.intValue());
            }

            @Override
            public void onFinish() {
                canAnswer = false;
                ansFeedbackTv.setText("Waktu sudah habis!");
                notAnswered++;
                showNextBtn();
            }
        }.start();
    }

    private void showNextBtn() {
        if (currentQueNo == totalQuestions) {
            next_btn.setText("Selesai");
            next_btn.setEnabled(true);
            next_btn.setVisibility(View.VISIBLE);
        } else {
            next_btn.setVisibility(View.VISIBLE);
            next_btn.setEnabled(true);
            ansFeedbackTv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.option_a) {
            verifyAnswer(option_a);
        } else if (v.getId() == R.id.option_b) {
            verifyAnswer(option_b);
        } else if (v.getId() == R.id.option_c) {
            verifyAnswer(option_c);
        } else if (v.getId() == R.id.next_btn) {
            if (currentQueNo == totalQuestions) {
                submitResults();
            } else {
                currentQueNo++;
                loadQuestions(currentQueNo);
                resetOptions();
            }
        }
    }

    private void resetOptions() {
        ansFeedbackTv.setVisibility(View.INVISIBLE);
        next_btn.setVisibility(View.INVISIBLE);
        next_btn.setEnabled(false);
        option_a.setBackground(ContextCompat.getDrawable(getContext() , R.color.light_sky));
        option_b.setBackground(ContextCompat.getDrawable(getContext() , R.color.light_sky));
        option_c.setBackground(ContextCompat.getDrawable(getContext() , R.color.light_sky));
    }

    private void submitResults() {
        HashMap<String , Object> resultMap = new HashMap<>();
        resultMap.put("Benar", correctAnswered);
        resultMap.put("Salah", wrongAnswered);
        resultMap.put("Tidak Dijawab", notAnswered);

        viewModel.addResults(resultMap);
        navController.navigate(R.id.action_quizFragment_to_resultFragment);
    }

    private void verifyAnswer(Button button) {
        if (canAnswer){
            if (answer.equals(button.getText())){
                button.setBackground(ContextCompat.getDrawable(getContext(), R.color.green));
                correctAnswered++;
                ansFeedbackTv.setText("Jawaban benar!");
            }else{
                button.setBackground(ContextCompat.getDrawable(getContext(), R.color.red));
                wrongAnswered++;
                ansFeedbackTv.setText("Jawaban salah! \nJawaban Benar :" + answer);
            }
        }
        canAnswer=false;
        countDownTimer.cancel();
        showNextBtn();
    }
}