package com.brafly.quizcuy.model;

import com.google.firebase.firestore.DocumentId;

public class QuizListModel {

    @DocumentId
    private String quizId;
    private String title, image;
    private long questions, difficulty;

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getQuestions() {
        return questions;
    }

    public void setQuestions(long questions) {
        this.questions = questions;
    }

    public long getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(long difficulty) {
        this.difficulty = difficulty;
    }

    public QuizListModel() {

    }
    public QuizListModel(String quizId, String title, String image, long questions, long difficulty) {
        this.quizId = quizId;
        this.title = title;
        this.image = image;
        this.questions = questions;
        this.difficulty = difficulty;
    }
}
