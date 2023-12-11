package com.brafly.quizcuy.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.brafly.quizcuy.model.QuestionModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Question {

    private FirebaseFirestore firestore;
    private String quizId;
    private MutableLiveData<List<QuestionModel>> questionMutableLiveData;
    private OnQuestionLoad onQuestionLoad;
    private OnResultAdded onResultAdded;
    private String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

    public void addResults(HashMap<String , Object> resultMap){
        firestore.collection("Quiz").document(quizId)
                .collection("Hasil").document(currentUserId)
                .set(resultMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            onResultAdded.onSubmit();
                        }else{
                            onResultAdded.onError(task.getException());
                        }
                    }
                });
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public Question(OnQuestionLoad onQuestionLoad, OnResultAdded onResultAdded) {
        firestore = FirebaseFirestore.getInstance();
        questionMutableLiveData = new MutableLiveData<>();
        this.onQuestionLoad = onQuestionLoad;
        this.onResultAdded = onResultAdded;
    }

    public void getQuestions() {
        firestore.collection("Quiz").document(quizId)
                .collection("questions").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            onQuestionLoad.onLoad(task.getResult().toObjects(QuestionModel.class));
                        } else {
                            onQuestionLoad.onError(task.getException());
                        }
                    }
                });
    }

    public interface OnQuestionLoad {
        void onLoad(List<QuestionModel> questionModels);
        void onError(Exception e);
    }
    public interface OnResultAdded{
        boolean onSubmit();
        void onError(Exception e);
    }
}
