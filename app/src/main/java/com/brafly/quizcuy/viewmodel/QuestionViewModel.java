package com.brafly.quizcuy.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.brafly.quizcuy.model.QuestionModel;
import com.brafly.quizcuy.repository.Question;

import java.util.HashMap;
import java.util.List;

public class QuestionViewModel extends ViewModel implements Question.OnQuestionLoad, Question.OnResultAdded {
    private MutableLiveData<List<QuestionModel>> questionMutableLiveData;
    private Question question;

    public MutableLiveData<List<QuestionModel>> getQuestionMutableLiveData() {
        return questionMutableLiveData;
    }

    public QuestionViewModel() {
        questionMutableLiveData = new MutableLiveData<>();
        question = new Question(this, this);
    }

    public void addResults(HashMap<String , Object> resultMap){
        question.addResults(resultMap);
    }

    public void setQuizId(String quizId) {
        question.setQuizId(quizId);
        question.getQuestions();
    }

    @Override
    public void onLoad(List<QuestionModel> questionModels) {
        questionMutableLiveData.setValue(questionModels);
    }

    @Override
    public boolean onSubmit() {
        return true;
    }

    @Override
    public void onError(Exception e) {
        Log.d("QuizError", "onError: " + e.getMessage());
    }
}
