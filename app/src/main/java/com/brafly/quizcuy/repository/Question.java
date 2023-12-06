package com.brafly.quizcuy.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.brafly.quizcuy.model.QuestionModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class Question {

    private FirebaseFirestore firestore;
    private String quizId;
    private MutableLiveData<List<QuestionModel>> questionMutableLiveData;
    private OnQuestionLoad onQuestionLoad;

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public Question(OnQuestionLoad onQuestionLoad) {
        firestore = FirebaseFirestore.getInstance();
        questionMutableLiveData = new MutableLiveData<>();
        this.onQuestionLoad = onQuestionLoad;
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
}
