package com.brafly.quizcuy.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.brafly.quizcuy.R;
import com.brafly.quizcuy.model.QuizListModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.QuizListViewHolder> {
    private List<QuizListModel> QuizListModels;

    public void setQuizListModels(List<QuizListModel> quizListModels) {
        QuizListModels = quizListModels;
    }

    @NonNull
    @Override
    public QuizListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_quiz, parent, false);
        return new QuizListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizListViewHolder holder, int position) {
        QuizListModel model = QuizListModels.get(position);
        holder.title.setText(model.getTitle());
        Glide.with(holder.itemView).load(model.getImage()).into(holder.quizImage);
    }

    @Override
    public int getItemCount() {
        return QuizListModels.size();
    }

    public class QuizListViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView quizImage;

        public QuizListViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.quiz_title);
            quizImage = itemView.findViewById(R.id.quiz_img);
        }
    }
}
