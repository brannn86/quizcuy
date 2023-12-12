package com.brafly.quizcuy.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.brafly.quizcuy.model.QuestionModel;
import com.brafly.quizcuy.repository.Question;

import java.util.HashMap;
import java.util.List;

public class QuestionViewModel extends ViewModel implements Question.OnQuestionLoad, Question.OnResultAdded, Question.OnResultLoad {
    private MutableLiveData<List<QuestionModel>> questionMutableLiveData;
    private Question question;

    private MutableLiveData<HashMap<String , Long>> resultMutableLiveData;

    public MutableLiveData<HashMap<String, Long>> getResultMutableLiveData() {
        return resultMutableLiveData;
    }

    public void getResults(){
        question.getResults();
    }

    public MutableLiveData<List<QuestionModel>> getQuestionMutableLiveData() {
        return questionMutableLiveData;
    }

    public QuestionViewModel() {
        questionMutableLiveData = new MutableLiveData<>();
        resultMutableLiveData = new MutableLiveData<>();
        question = new Question(this, this, this);
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
    public void OnResultLoad(HashMap<String, Long> resultMap) {
        resultMutableLiveData.setValue(resultMap);
    }

    @Override
    public void onError(Exception e) {
        Log.d("QuizError", "onError: " + e.getMessage());
    }
}
