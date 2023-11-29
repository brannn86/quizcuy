package com.brafly.quizcuy.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.brafly.quizcuy.R;
import com.brafly.quizcuy.model.QuizListModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.QuizListViewHolder> {
    private List<QuizListModel> QuizListModels;
    private OnItemClickListener onItemClickListener;

    public void setQuizListModels(List<QuizListModel> quizListModels) {
        QuizListModels = quizListModels;
    }

    public QuizAdapter(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
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
        if (QuizListModels == null) {
            return 0;
        } else {
            return QuizListModels.size();
        }
    }

    public class QuizListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title;
        private ImageView quizImage;
        private ConstraintLayout constraintLayout;

        public QuizListViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.quiz_title);
            quizImage = itemView.findViewById(R.id.quiz_img);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);

            constraintLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
